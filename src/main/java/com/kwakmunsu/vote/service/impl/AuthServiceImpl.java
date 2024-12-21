package com.kwakmunsu.vote.service.impl;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.dto.AuthDto;
import com.kwakmunsu.vote.dto.AuthDto.LoginRequest;
import com.kwakmunsu.vote.dto.AuthDto.ReissueRequest;
import com.kwakmunsu.vote.dto.AuthDto.SignUpRequest;
import com.kwakmunsu.vote.dto.AuthDto.TokenResponse;
import com.kwakmunsu.vote.dto.AuthDto.UpdateRequest;
import com.kwakmunsu.vote.jwt.JWTProvider;
import com.kwakmunsu.vote.jwt.JWTUtil;
import com.kwakmunsu.vote.jwt.dto.TokenValidation;
import com.kwakmunsu.vote.repository.UserRepository;
import com.kwakmunsu.vote.response.exception.UserException;
import com.kwakmunsu.vote.service.AuthService;
import com.kwakmunsu.vote.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
========== login logic ================
1. login 요청 시 validAuth()로 사용자 인증을 함 -> token 생성 후 authenticationManager에게 토큰 던진 후 UserDetailService의
loadUserByUsername를 통해 DB에서 검증
인증된 사용자에 대한 Authentication 객체를 반환. 실패 시 예외 던짐.
2. DB 에서 user 정보 가져온 후 refreshtoken 정보 가져온 후 유효성 검증 ( 잘못됐나, 만료인가)
2-1. null 이거나 유효하지 않을 시 refreshtoken 생성 후 쿠키와 DB에 저장. 쿠키는 보안을 위해 응답 객체에 넣지 않고 쿠키로 보낸다.
3. accesstoken은 Rt가 유효하던 안하던 무조건 생성됨.
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final JWTProvider tokenProvider;
    private final JWTUtil jwtUtil;

    @Transactional
    @Override
    public void signup(SignUpRequest signUpRequestDto) {

        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());
        User user = signUpRequestDto.toEntity(encodedPassword);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public AuthDto.TokenResponse login(LoginRequest loginRequestDto, HttpServletResponse response) {
        Authentication authentication = validAuth(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());

        User user = userService.findUser(Long.valueOf(authentication.getName()));
        String refreshToken = user.getRefreshToken();

        AuthDto.TokenResponse tokenResponseDto;
        AuthDto.RefreshTokenResponse refreshTokenResponse;
        TokenValidation tokenValidation = tokenProvider.validateToken(refreshToken); // 유효성 검증

        if (refreshToken == null || !tokenValidation.isValid()) {  // rt가 DB에 없거나 유효하지 않을때. (만료됐거나 등)  -> at/rt update
            refreshTokenResponse = tokenProvider.generateRefreshToken(authentication);
            String newRefreshToken = refreshTokenResponse.getRefreshToken();
            Cookie refreshCookie = createCookie("REFRESH", newRefreshToken,14 * 24 * 60 * 60); // 쿠키 2 week rt랑 동일
            response.addCookie(refreshCookie); // refreshToken을 쿠키에 담아서 응답에 추가
            user.updateRefreshToken(newRefreshToken);   // Transaction 영속성 컨텍스트 특성으로 인해 변경 감지하고 자동 DB update
        }
        tokenResponseDto = tokenProvider.generateAccessToken(authentication); // at rt 빌급

        return tokenResponseDto;
    }

    @Override
    public void updatePassword(UpdateRequest updateRequestDto) {

    }

    @Override // Refresh Token으로 Access Token 재발급 메소드
    public TokenResponse reissue(ReissueRequest reissueRequestDto, HttpServletRequest request) {
        String refreshToken = getCookie(request); // 쿠키에서 RT 추출

        Authentication authentication = checkRefreshToken(refreshToken); // check RT
        Long userId = Long.valueOf(authentication.getName());
        log.info("rt id: " + userId);
        // DB의 사용자 Refresh Token 값과, 전달받은 Refresh Token의 불일치 여부 검사 -> 공격자가 유효한 형식으로 보낼수있음.
        String dbRefreshToken = userRepository.findRefreshTokenById(userId);
        if(dbRefreshToken == null || !(dbRefreshToken.equals(refreshToken))) {
            throw new UserException.TokenBadRequest("Refresh Token = " + refreshToken);
        }
       AuthDto.TokenResponse tokenResponseDto = tokenProvider.generateAccessToken(authentication); // at 빌급
        return tokenResponseDto;
    }

    @Override
    public void logout(HttpServletRequest request,HttpServletResponse response) {
        String refreshToken = getCookie(request); // 쿠키에서 RT 추출
        log.info("logout RT: " +  refreshToken);
        Authentication authentication = checkRefreshToken(refreshToken); // check RT
        Long userId = Long.valueOf(authentication.getName());
        // DB의 사용자 Refresh Token 값과, 전달받은 Refresh Token의 일치 여부 검사 -> 공격자가 유효한 형식으로 보낼수있음.
        String dbRefreshToken = userRepository.findRefreshTokenById(userId);
        log.info("dbRefreshToken RT: " +  dbRefreshToken);
        if(dbRefreshToken == null || !(dbRefreshToken.equals(refreshToken))) {
            throw new UserException.TokenBadRequest("Refresh Token = " + refreshToken);
        }
        userRepository.deleteRefreshTokenById(userId); // DB  RT 삭제.
        Cookie resetCookie = createCookie("REFRESH", null,0);
        response.addCookie(resetCookie); // refreshToken을 쿠키에 담아서 응답에 추가
    }

    @Override
    public boolean checkUsername(String username) {
      return userRepository.existsByUsername(username);

    }

    @Override
    public boolean checkNickname(String nickname) {
       return userRepository.existsByNickname(nickname);
    }


    //        ============ 유틸성 메소드' ================
    // 반환된 객체로 아이디와 비밀번호가 일치하는지 검증하는 로직에 활용이 가능함.
    private static UsernamePasswordAuthenticationToken toAuthentication(String email,
            String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private Authentication validAuth(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = toAuthentication(
                username, password);
        Authentication authentication = authenticationManagerBuilder
                .getObject().authenticate(authenticationToken);  // DB에서 아이디와 비밀번호가 일치하는지 검증.
        return authentication;
    }

    private Authentication checkRefreshToken(String refreshToken) {
        TokenValidation tokenValidation = tokenProvider.validateToken(refreshToken); // 유효성 검증
        if (!tokenValidation.isValid()) {
            throw new JwtException("입력한 Refresh Token은 잘못된 토큰입니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            throw new UserException.TokenBadRequest("refresh 토큰이 아닙니다 = " + refreshToken);
        }

        return authentication;
    }

    private Cookie createCookie(String key, String value, int age) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(age);
        //cookie.setSecure(true);
        cookie.setPath("/"); // 쿠키 사용 경로
        cookie.setHttpOnly(true); // 앞단에서 쿠키 접근 못하게 함. 필수임.    // JavaScript에서 접근 불가

        return cookie;
    }
    private String getCookie(HttpServletRequest request) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("REFRESH")) {

                refresh = cookie.getValue();
            }
        }
        if (refresh == null) {

            //response status code
//            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }
        return refresh;
    }
}

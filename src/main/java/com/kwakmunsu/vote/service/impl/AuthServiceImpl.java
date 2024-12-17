package com.kwakmunsu.vote.service.impl;

import com.kwakmunsu.vote.domain.User;
import com.kwakmunsu.vote.dto.AuthDto;
import com.kwakmunsu.vote.dto.AuthDto.LoginRequest;
import com.kwakmunsu.vote.dto.AuthDto.SignUpRequest;
import com.kwakmunsu.vote.dto.AuthDto.TokenResponse;
import com.kwakmunsu.vote.dto.AuthDto.UpdateRequest;
import com.kwakmunsu.vote.jwt.JWTProvider;
import com.kwakmunsu.vote.jwt.dto.TokenValidation;
import com.kwakmunsu.vote.repository.UserRepository;
import com.kwakmunsu.vote.response.exception.UserException;
import com.kwakmunsu.vote.service.AuthService;
import com.kwakmunsu.vote.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final JWTProvider tokenProvider;

    @Transactional
    @Override
    public void signup(SignUpRequest signUpRequestDto) {
        // 중복 체크
        String username = signUpRequestDto.getUsername();
        String nickname = signUpRequestDto.getNickname();
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UserException.UserNameDuplicate(username);
                });
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new UserException.NickNameDuplicate(nickname);
                });
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());
        User user = signUpRequestDto.toEntity(encodedPassword);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public AuthDto.TokenResponse login(LoginRequest loginRequestDto, HttpServletResponse response) {
        Authentication authentication = vaildAuth(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());

        User user = userService.findUser(Long.valueOf(authentication.getName()));
        String refreshToken = user.getRefreshToken();

        AuthDto.TokenResponse tokenResponseDto;
        AuthDto.RefreshTokenResponse refreshTokenResponse;
        TokenValidation tokenValidation = tokenProvider.validateToken(refreshToken); // 유효성 검증

        if (refreshToken == null || !tokenValidation.isValid()) {  // rt가 DB에 없거나 유효하지 않을때. (만료됐거나 등)  -> at/rt update
            refreshTokenResponse = tokenProvider.generateRefreshToken(authentication);
            String newRefreshToken = refreshTokenResponse.getRefreshToken();
            Cookie refreshCookie = createCookie("REFRESH", newRefreshToken);
            response.addCookie(refreshCookie); // refreshToken을 쿠키에 담아서 응답에 추가
            user.updateRefreshToken(newRefreshToken);   // Transaction 영속성 컨텍스트 특성으로 인해 변경 감지하고 자동 DB update
        }
        tokenResponseDto = tokenProvider.generateAccessToken(authentication); // at rt 빌급

        return tokenResponseDto;
    }

    @Override
    public void updatePassword(UpdateRequest updateRequestDto) {

    }

    // ============ 유틸성 메소드' ================
    // 반환된 객체로 아이디와 비밀번호가 일치하는지 검증하는 로직에 활용이 가능함.
    private static UsernamePasswordAuthenticationToken toAuthentication(String email,
            String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private Authentication vaildAuth(String username, String password) {
        // Spring Security의 인증 메커니즘을 사용하는 것이 보다 안전하고 확장 가능한 방법
        UsernamePasswordAuthenticationToken authenticationToken = toAuthentication(
                username, password);
        Authentication authentication = authenticationManagerBuilder
                .getObject().authenticate(authenticationToken);  // 아이디와 비밀번호가 일치하는지 검증.
        return authentication;
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(14 * 24 * 60 * 60); // 쿠키 2 week rt랑 동일
        //cookie.setSecure(true);
        cookie.setPath("/"); // 쿠키 사용 경로
        cookie.setHttpOnly(true); // 앞단에서 쿠키 접근 못하게 함. 필수임.    // JavaScript에서 접근 불가

        return cookie;
    }
}

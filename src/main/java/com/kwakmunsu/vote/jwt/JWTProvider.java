package com.kwakmunsu.vote.jwt;

import com.kwakmunsu.vote.dto.AuthDto;
import com.kwakmunsu.vote.jwt.dto.TokenValidation;
import com.kwakmunsu.vote.response.ResponseCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class JWTProvider {

    @Value("${spring.jwt.access.expiration}")
    private Long accessTokenExpireTime;
    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshTokenExpireTime;

    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";
    private static final String BEARER_TYPE = "Bearer";
    private final JWTUtil jwtUtil;



    // 응답에는 at만 던져주고 rf는 쿠키로 던져줄예정이라 따로 정의해서 가져올 예정.
    // access
    public AuthDto.TokenResponse generateAccessToken(Authentication authentication) {
        String at = createToken(authentication,ACCESS,5 * 1000L);
        long expiration = jwtUtil.getExpiration(at);

        log.info("at: " + jwtUtil.getClaimsFromToken(at));
        return AuthDto.TokenResponse.builder()
                .grantType(BEARER_TYPE)
                .accessToken(at)
                .accessTokenExpiresIn(expiration)
                .build();
    }

    // refresh 토큰 발급.
    public AuthDto.RefreshTokenResponse generateRefreshToken(Authentication authentication) {
        String rt = createToken(authentication,REFRESH,refreshTokenExpireTime);
        return AuthDto.RefreshTokenResponse.builder()
                .refreshToken(rt)
                .build();
    }

    // accessToken / refershToken 생성 -> category로 구분
    public String createToken(Authentication authentication,String category,long expiredMs) {
        // 권한 가져오는 로직
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String authority = auth.getAuthority();
       return jwtUtil.creatToken(authentication,category,authority,expiredMs);

    }



    // JWT에서 토큰을 이용해 인증 정보를 추출 후 UsernamePasswordAuthenticationToken을 생성해 전달
    // Authentication 객체를 생성하고, 이를 SecurityContext에 설정하여 이후의 요청에서 인증 정보를 사용할 수 있도록 함.
    public Authentication getAuthentication(String token) {

        log.info("getAuthentication" + token);
        String username  = jwtUtil.getUsername(token);
        // 유저 권한은 하나밖에 없기에 singletonList로 진행함. 단 하나의 권한만 가질때 사용.
        String auth = jwtUtil.getAuthority(token);
        log.info(auth);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auth);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(grantedAuthority);

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }


    // 토큰 유효성 체크
    public TokenValidation validateToken(String token) {
        try {
            Jws<Claims> claims = jwtUtil.getClaimsFromToken(token);

            // 유효할 시 유효 메세지를 넣어주며 반환
            return new TokenValidation(ResponseCode.TOKEN_IS_VALID);

        } catch (ExpiredJwtException e) {

            return new TokenValidation(ResponseCode.TOKEN_EXPIRED);
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명");
            return new TokenValidation(ResponseCode.TOKEN_ERROR);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 서명");
            return new TokenValidation(ResponseCode.TOKEN_HASH_NOT_SUPPORTED);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰");
            return new TokenValidation(ResponseCode.TOKEN_ERROR);
        }

    }
}

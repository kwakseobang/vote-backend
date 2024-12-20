package com.kwakmunsu.vote.jwt;

import com.kwakmunsu.vote.jwt.dto.TokenValidation;
import com.kwakmunsu.vote.response.ResponseCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JWTProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        // 빈 문자열("")**이나 공백만 있는 문자열은 false
        if(!StringUtils.hasText(token)) {
            handleMissingToken(request, response, filterChain);
            return;
        }

        TokenValidation tokenValidation = tokenProvider.validateToken(token);
        // 토큰이 유효하지 않을 시.
        if(!tokenValidation.isValid()) {
            handleWrongToken(request, response, filterChain, tokenValidation);
            return;
        }


        handleValidToken(token);
        filterChain.doFilter(request, response);

    }


    // 요청 헤더에 Authorization를 보면 접두사 Bearer가 포함되어있음. 제외하고 실제 Access 토큰을 가져오는 함수
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);  // 접두사 "Bearer "을 제외하고 실제 토큰 문자열을 반환.
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/login","/logout","/signup","/reissue","/swagger/**","/swagger-ui/**","/v3/api-docs/**"};//"/v3/api-docs/**" 추가해줘야 swagger 작동
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
    // JWT에서 토큰을 이용해 인증 정보를 추출 후 UsernamePasswordAuthenticationToken을 생성해 전달
    // Authentication 객체를 생성하고, 이를 SecurityContext에 설정하여 이후의 요청에서 인증 정보를 사용할 수 있도록 힘.
    private void handleValidToken(String token) {

        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("AUTH SUCCESS : {}", authentication.getName());
    }


    // 인증 오류 시
    // JwtFilter에서 발생한 예외 처리는 ExceptionHandler가아닌, 앞단의 JwtExceptionFilter에게 던져짐.
    // filter는 스프링 컨테이너 전 앞단에서 진행되기 때문이다.
    private void handleWrongToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
            TokenValidation tokenValidation) throws IOException, ServletException {
        request.setAttribute("result", tokenValidation);
        filterChain.doFilter(request, response);
    }

    private void handleMissingToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        request.setAttribute("result",
                new TokenValidation(ResponseCode.WRONG_AUTH_HEADER));
        filterChain.doFilter(request, response);
    }

}

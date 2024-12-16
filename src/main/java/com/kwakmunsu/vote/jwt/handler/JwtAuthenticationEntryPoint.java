package com.kwakmunsu.vote.jwt.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwakmunsu.vote.jwt.dto.TokenValidation;
import com.kwakmunsu.vote.jwt.response.JwtExceptionResponse;
import com.kwakmunsu.vote.response.ResponseCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// 401 에러 토큰 인증 에러 시 여기서 에러를 잡는다.
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String VALIDATION_RESULT_KEY = "result";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 인증과정에서 오류 발생 시 아래 함수 호출
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        TokenValidation result = (TokenValidation) request.getAttribute(VALIDATION_RESULT_KEY);
        ResponseCode error;

        switch (result.getTokenStatus()) {
            case TOKEN_EXPIRED -> error = ResponseCode.TOKEN_EXPIRED;
            case TOKEN_IS_BLACKLIST -> error = ResponseCode.TOKEN_IS_BLACKLIST;
            case TOKEN_ERROR -> error = ResponseCode.TOKEN_ERROR;
            case TOKEN_HASH_NOT_SUPPORTED -> error = ResponseCode.TOKEN_HASH_NOT_SUPPORTED;
            case WRONG_AUTH_HEADER -> error = ResponseCode.WRONG_AUTH_HEADER;
            default -> {
                error = ResponseCode.TOKEN_VALIDATION_TRY_FAILED;
            }
        }
        sendError(response, error.getMessage(), error.getHttpStatus());
    }

    private void sendError(HttpServletResponse response, String msg, int code) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        JwtExceptionResponse responseJson = new JwtExceptionResponse(
                HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED),
                code,
                msg,
                LocalDateTime.now().toString()
        );

        String jsonToString = objectMapper.writeValueAsString(responseJson);
        response.getWriter().write(jsonToString);
    }
}

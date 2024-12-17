package com.kwakmunsu.vote.jwt.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@NoArgsConstructor
public class JwtExceptionResponse {
    public HttpStatus httpStatus; // 상태 메세지
    private int status; // 상태 코드
    private String message; // 상태 메세지
    private String timestamp;



    // 오류
    public JwtExceptionResponse(HttpStatus httpStatus,int status, String message, String timestamp) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }


}


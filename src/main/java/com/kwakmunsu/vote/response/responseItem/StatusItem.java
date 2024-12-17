package com.kwakmunsu.vote.response.responseItem;

import org.springframework.http.HttpStatus;

public class StatusItem {
    public static final int OK = HttpStatus.OK.value(); // 200
    public static final int CREATED = HttpStatus.CREATED.value(); // 201
    public static final int NO_CONTENT = HttpStatus.NO_CONTENT.value(); // 204
    public static final int BAD_REQUEST =  HttpStatus.BAD_REQUEST.value(); //400
    public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value(); //401
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value(); //403
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value(); //404
    public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value(); //500


    // 토큰 전용
    // Token 전용
    public static final int TOKEN_EXPIRED = 4011;
    public static final int TOKEN_IS_BLACKLIST = 4012;
    public static final int TOKEN_ERROR = 4013;
    public static final int TOKEN_HASH_NOT_SUPPORTED = 4014;
    public static final int NO_AUTH_HEADER = 4015;
    public static final int TOKEN_VALIDATION_TRY_FAILED = 4016;
}

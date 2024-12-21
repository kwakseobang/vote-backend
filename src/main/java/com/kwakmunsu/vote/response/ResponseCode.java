package com.kwakmunsu.vote.response;

import com.kwakmunsu.vote.response.responseItem.MessageItem;
import com.kwakmunsu.vote.response.responseItem.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode  {
    // ===================== //

    // User 관련 성공 응답
    CREATED_USER(StatusItem.CREATED, MessageItem.CREATED_USER),
    READ_USER(StatusItem.OK, MessageItem.READ_USER),
    UPDATE_USER(StatusItem.NO_CONTENT, MessageItem.UPDATE_USER),
    DELETE_USER(StatusItem.NO_CONTENT, MessageItem.DELETE_USER),

    // User 관련 실패 응답
    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),
    BAD_REQUEST_USER(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_USER),
    DUPLICATE_ID(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_ID),
    DUPLICATE_NICKNAME(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_NICKNAME),

    // ===================== //

    // vote 관련 성공 응답
    CREATED_VOTE(StatusItem.CREATED, MessageItem.CREATED_VOTE),
    READ_VOTE(StatusItem.OK, MessageItem.READ_VOTE),
    READ_VOTES(StatusItem.OK, MessageItem.READ_VOTES),
    UPDATE_VOTE(StatusItem.NO_CONTENT, MessageItem.UPDATE_VOTE),
    DELETE_VOTE(StatusItem.NO_CONTENT, MessageItem.DELETE_VOTE),

    // vote 관련 실패 응답
    NOT_FOUND_VOTE(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_VOTE),
    BAD_REQUEST_VOTE(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_VOTE),

    // ===================== //

    // Token 성공 응답
    REISSUE_SUCCESS(StatusItem.OK, MessageItem.REISSUE_SUCCESS),
    // 토큰 유효 응답
    TOKEN_IS_VALID(StatusItem.OK, MessageItem.TOKEN_IS_VALID),
    // Token 실패 응답
    TOKEN_EXPIRED(StatusItem.TOKEN_EXPIRED, MessageItem.TOKEN_EXPIRED),
    TOKEN_ERROR(StatusItem.TOKEN_ERROR, MessageItem.TOKEN_ERROR),
    BAD_REQUEST_TOKEN(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_TOKEN),
    TOKEN_IS_BLACKLIST(StatusItem.TOKEN_IS_BLACKLIST,MessageItem.TOKEN_IS_BLACKLIST),
    TOKEN_HASH_NOT_SUPPORTED(StatusItem.TOKEN_HASH_NOT_SUPPORTED,MessageItem.TOKEN_HASH_NOT_SUPPORTED),
    WRONG_AUTH_HEADER(StatusItem.NO_AUTH_HEADER,MessageItem.WRONG_AUTH_HEADER),
    TOKEN_VALIDATION_TRY_FAILED(StatusItem.TOKEN_VALIDATION_TRY_FAILED,MessageItem.TOKEN_VALIDATION_TRY_FAILED),


    // ===================== //

    // 기타 성공 응답
    READ_IS_LOGIN(StatusItem.OK, MessageItem.READ_IS_LOGIN),
    LOGIN_SUCCESS(StatusItem.OK, MessageItem.LOGIN_SUCCESS),
    USERNAME_SUCCESS(StatusItem.OK, MessageItem.USERNAME_SUCCESS),
    NICKNAME_SUCCESS(StatusItem.OK, MessageItem.NICKNAME_SUCCESS),
    LOGOUT_SUCCESS(StatusItem.OK, MessageItem.LOGOUT_SUCCESS),
    UPDATE_PASSWORD(StatusItem.NO_CONTENT, MessageItem.UPDATE_PASSWORD),
    PREVENT_GET_ERROR(StatusItem.NO_CONTENT, MessageItem.PREVENT_GET_ERROR),

    // 기타 실패 응답
    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ERROR(StatusItem.UNAUTHORIZED, MessageItem.UNAUTHORIZED),
    FORBIDDEN_ERROR(StatusItem.FORBIDDEN, MessageItem.FORBIDDEN),
    ;


    private  int httpStatus;
    private String message;

}

package com.kwakmunsu.vote.response.exception;

import com.kwakmunsu.vote.response.ResponseCode;

public class UserException extends CustomException{

    public UserException(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }

    public static class UserNameDuplicate extends UserException {
        public UserNameDuplicate(String message) {
            super(ResponseCode.DUPLICATE_ID, "duplicate : " + message);
        }
    }

    public static class NickNameDuplicate extends UserException {
        public NickNameDuplicate(String message) {
            super(ResponseCode.DUPLICATE_NICKNAME, "duplicate : " + message);
        }
    }

    public static class UserNotFound extends UserException {
        public UserNotFound(String message) {
            super(ResponseCode.NOT_FOUND_USER, message);
        }
    }

    public static class UserBadRequest extends UserException {
        public UserBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_USER, message);
        }
    }

    public static class TokenBadRequest extends UserException {
        public TokenBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_TOKEN, message);
        }
    }

    public static class UNAUTHORIZED extends UserException {
        public UNAUTHORIZED(String message) {
            super(ResponseCode.UNAUTHORIZED_ERROR, message);
        }
    }

}

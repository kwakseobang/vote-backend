package com.kwakmunsu.vote.response.exception;

import com.kwakmunsu.vote.response.ResponseCode;
import com.kwakmunsu.vote.response.responseItem.MessageItem;
import com.kwakmunsu.vote.response.responseItem.StatusItem;

public class VoteException extends CustomException{

    public VoteException(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }

    public static class VoteNotFound extends VoteException {
        public VoteNotFound(String message) {
            super(ResponseCode.NOT_FOUND_VOTE, message);
        }
    }

    public static class VoteBadRequest extends VoteException {
        public VoteBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_VOTE, message);
        }
    }
}

package com.kwakmunsu.vote.response;


import com.kwakmunsu.vote.response.exception.CustomException;
import com.kwakmunsu.vote.response.exception.UserException;
import com.kwakmunsu.vote.response.exception.VoteException;
import java.nio.file.AccessDeniedException;
import javax.naming.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> handleException() {
        return ResponseData.toResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseData> handleUnauthorizedException() {
        return ResponseData.toResponseEntity(ResponseCode.UNAUTHORIZED_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseData> handleForbiddenException() {
        return ResponseData.toResponseEntity(ResponseCode.FORBIDDEN_ERROR);
    }


    // CustomException

    @ExceptionHandler({UserException.class, VoteException.class})
    public ResponseEntity<ResponseData> handleCustomException(CustomException ex) {
        return ResponseData.toResponseEntity(ex.getErrorResponseCode());
    }
}

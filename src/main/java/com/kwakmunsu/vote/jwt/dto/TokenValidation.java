package com.kwakmunsu.vote.jwt.dto;


import com.kwakmunsu.vote.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TokenValidation {
    private ResponseCode tokenStatus;

    // 토큰 유효한지 확인.
    public boolean isValid() {
        return ResponseCode.TOKEN_IS_VALID == this.tokenStatus;
    }

}
package com.kwakmunsu.vote.dto;


import com.kwakmunsu.vote.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    // ====== requestDto ======

    @Getter
    @NoArgsConstructor
    public static class SignUpRequest {

        private String username;
        private String password;
        private String nickname;

        public User toEntity(String password) {
            return User.UserSaveBuilder()
                    .username(this.username)
                    .password(password) // 암호화
                    .nickname(this.nickname)
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor
    public static class ReissueRequest {

        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {

        private String username;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        private String username;
        private String password;
        private String newPassword;
    }

    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenResponse {

        private String grantType; // Bearer
        private String accessToken;
        private Long accessTokenExpiresIn;
        private String refreshToken;
    }

}

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
        //rt는 보안 강화를 위해 쿠키에 담아서 보냄
    }
    @Builder
    @Getter
    @AllArgsConstructor
    public static class RefreshTokenResponse {
        // rt는 쿠키에 담아서 보내는데 발급 후 service단에서 가져오기 위해서 만듦
        private String refreshToken;
    }

}

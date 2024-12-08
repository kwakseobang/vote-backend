package com.kwakmunsu.vote.dto;


import com.kwakmunsu.vote.domain.User;
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

        public User toEntity() {
            return User.UserSaveBuilder()
                    .username(this.username)
                    .password(this.password)
                    .nickname(this.nickname)
                    .build();
        }
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


}

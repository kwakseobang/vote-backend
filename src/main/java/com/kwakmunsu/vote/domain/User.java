package com.kwakmunsu.vote.domain;


import com.kwakmunsu.vote.domain.enums.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name ="user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "nickname", unique = true)
    private String nickname;

    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;


    @Builder(builderClassName = "UserSaveBuilder", builderMethodName = "UserSaveBuilder")
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authority = Authority.ROLE_USER;
        //  (refreshToken= null)
    }

    @Builder(builderClassName = "UserTokenSaveBuilder", builderMethodName = "UserTokenSaveBuilder")
    public User(String username,Authority authority) {
        this.username = username;
        this.authority = authority;
    }

    // refresh 업데이트
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

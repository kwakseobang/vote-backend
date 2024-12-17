package com.kwakmunsu.vote.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String CATEGORY_KEY = "category";
    private static final String USERNAME_KEY = "username";
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secretKey}")String secretKey) {

        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String  getCategory(String token) {
        return getClaimsFromToken(token).getPayload().get(CATEGORY_KEY, String.class);
    }

    public String getUsername(String token) {

        return getClaimsFromToken(token).getPayload().get(USERNAME_KEY, String.class);
    }

    public String getAuthority(String token) {

        return getClaimsFromToken(token).getPayload().get(AUTHORITIES_KEY, String.class);
    }
    public Long getExpiration(String token) {
        return getClaimsFromToken(token).getPayload().getExpiration().getTime();
    }
    // 만료 시 true
    public Boolean isExpired(String token) {

        return getClaimsFromToken(token).getPayload().getExpiration().before(new Date());
    }

    // token의 claims 가져오는 함수.
    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }


    public String createAccessJwt(Authentication authentication,String category,String auth,Long expiredMs ) {


        Date date = new Date();
        Date validity = new Date(date.getTime() + expiredMs);
        return Jwts.builder()
                .claim(CATEGORY_KEY,category)
                .claim(USERNAME_KEY, authentication.getName())
                .claim(AUTHORITIES_KEY, auth)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshJwt(String category,Long expiredMs) {
        Date date = new Date();
        Date validity = new Date(date.getTime() + expiredMs);

        return Jwts.builder()
                .claim(CATEGORY_KEY,category)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

}

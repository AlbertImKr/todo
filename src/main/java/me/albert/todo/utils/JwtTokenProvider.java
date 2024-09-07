package me.albert.todo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

    private final Key secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenProvider(
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    /**
     * 토큰 생성
     *
     * @param username 사용자 이름
     * @param signTime 토큰 발급 시간
     * @return 액세스 토큰
     */
    public String generateAccessToken(String username, LocalDateTime signTime) {
        var expiration = signTime.plusSeconds(accessTokenExpiration);
        return createToken(username, Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param username 사용자 이름
     * @param signTime 토큰 발급 시간
     * @return 리프레시 토큰
     */
    public String generateRefreshToken(String username, LocalDateTime signTime) {
        var expiration = signTime.plusSeconds(refreshTokenExpiration);
        return createToken(username, Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * 토큰에서 사용자 이름 추출
     *
     * @param token 토큰
     * @return 사용자 이름
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 토큰 유효성 검사
     *
     * @param token 토큰
     * @return 유효 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰 생성
     *
     * @param username   사용자 이름
     * @param expiration 만료 시간
     * @return 토큰
     */
    public String createToken(String username, Date expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}

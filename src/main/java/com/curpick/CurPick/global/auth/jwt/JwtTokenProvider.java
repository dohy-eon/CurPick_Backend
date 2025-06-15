package com.curpick.CurPick.global.auth.jwt;

import com.curpick.CurPick.domain.user.entity.User;
import com.curpick.CurPick.global.auth.userdetails.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Component
public class JwtTokenProvider {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenValidity = 1000 * 60 * 30;          // 30분
    private final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7일

    public String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("nickname", user.getNickname());
        claims.put("auth", "ROLE_" + user.getRole().name());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * 60 * 60); // 토큰 유효기간 설정 (1시간)

        return Jwts.builder()
                .setClaims(claims) // 사용자 정보를 담은 클레임
                .setIssuedAt(now)  // 토큰 생성 시간
                .setExpiration(validity) // 토큰 만료 시간
                .signWith(secretKey) // 서명 키
                .compact(); // 토큰 생성
    }

    public String createRefreshToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("auth", "ROLE_" + user.getRole().name())  // 권한 정보 추가
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidity))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    Long id = claims.get("id", Long.class);  // 클레임에서 id 추출
    String username = claims.getSubject();  // 기본적으로 Subject에 저장된 username
    String email = claims.get("email", String.class);
    String nickname = claims.get("nickname", String.class);
    String authority = claims.get("auth", String.class); 

    // 권한 생성
    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);

    // 실제 User 객체를 작성
    User user = User.builder()
            .id(id)
            .username(username)
            .password("") // JWT에는 비밀번호를 보통 포함하지 않음
            .email(email)
            .nickname(nickname)
            .build();

    // UserDetailsImpl 사용
    UserDetailsImpl userDetails = new UserDetailsImpl(user);

    return new UsernamePasswordAuthenticationToken(
            userDetails,
            "",
            Collections.singleton(grantedAuthority)
    );
}
public Claims getClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
}
}
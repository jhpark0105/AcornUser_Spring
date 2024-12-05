package com.erp.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	
	@Value("${secret-key}") // application.properties에 등록한 secretKey 가져옴
	private String secretKey;
	
	
	// JWT 생성
    public String create(String id) {
        // JWT 만료 시간 설정 (1시간 후)
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // JWT 생성
        String jwt = Jwts.builder()
        		.signWith(SignatureAlgorithm.HS256, secretKey) // 서명 및 알고리즘 설정
                .setSubject(id) // 주체 설정
                .setIssuedAt(new Date()) // 생성 시간 설정
                .setExpiration(expiredDate) // 만료 시간 설정
                .compact();
        
        return jwt;
    }

    // JWT 검증
    public String validate(String jwt) {
        try {
            // JWT 파싱 및 검증
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 검증용 키 설정
                    .build() // 파서 빌드
                    .parseClaimsJws(jwt) // JWT 파싱
                    .getBody();

            return claims.getSubject(); // 주체 반환
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 유효하지 않은 JWT
        }
    }
}

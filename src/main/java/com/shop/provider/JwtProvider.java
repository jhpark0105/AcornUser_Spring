package com.shop.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// JwtProvider : JWT 처리에 필요한 로직을 제공하는 클래스, 토큰의 생성과 유효성을 검증하고, 사용자 정보를 추출하는 역할
@Component
public class JwtProvider {
	
	@Value("${secret-key}") // @Value : application.properties에 등록한 secretKey를 가져온다.
	private String secretKey;
	
	
	// JWT 생성
    public String create(String id) {
        // JWT 만료 시간 설정 (24시간 후)
        Date expiredDate = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));

        // JWT 생성
        String jwt = Jwts.builder()
        		.signWith(SignatureAlgorithm.HS256, secretKey) // 서명 및 알고리즘 설정
                .setSubject(id) // JWT 주체 설정
                .setIssuedAt(new Date()) // JWT 생성 시간 설정
                .setExpiration(expiredDate) // JWT 만료 시간 설정
                .compact(); // JWT 문자열로 반환
        
        return jwt; // 생성된 JWT 반환
    }

    // JWT 검증
    public String validate(String jwt) {
        try {
            // JWT 파싱 및 검증
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // JWT 검증용 키 설정
                    .build() // JWT 파서 빌드
                    .parseClaimsJws(jwt) // JWT 파싱 및 검증
                    .getBody(); // JWT 본문(claim) 가져오기

            return claims.getSubject(); // JWT 주체 반환
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 유효하지 않은 JWT일 경우 null 반환
        }
    }
}

package com.shop.fliter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


// JwtAuthenticationFilter :
// Spring Security의 필터로, JWT를 사용하여 요청을 인증하는 역할, 이 필터는 모든 요청에 대해 JWT가 유효한지 확인하고, 유효한 경우 인증 정보를 SecurityContext에 설정한다.
// OncePerRequestFilter :
// Spring Framework에서 제공하는 추상 클래스, 이 클래스는 필터가 요청당 한 번만 실행되도록 보장하는 기능을 제공한다. 즉, 동일한 요청에 대해 여러 번 호출되지 않도록 하는 역할을 한다.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// JWT 처리에 필요한 JwtProvider 의존성 주입
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			// 요청 헤더와 쿠키에서 토큰 추출
			String token = parseBearerToken(request); // Authorization 헤더에서 토큰 추출
			if (token == null) {
				token = extractTokenFromCookie(request); // 쿠키에서 토큰 추출
			}

			if (token == null) {
				// 토큰이 없다면 다음 필터로 진행
				filterChain.doFilter(request, response);
				return;
			}

			// JWT 토큰의 유효성을 검증하고 사용자 ID를 반환
			String id = jwtProvider.validate(token);
			if (id == null) {
				// 유효한 토큰이 아니라면 다음 필터로 진행
				filterChain.doFilter(request, response);
				return;
			}

			// 인증 정보를 담는 객체 생성 (권한 정보는 구현 예정)
			AbstractAuthenticationToken abstractAuthenticationToken =
					new UsernamePasswordAuthenticationToken(id, null, AuthorityUtils.NO_AUTHORITIES);

			// 인증 요청에 대한 세부정보 설정
			abstractAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			// SecurityContext에 등록
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
			securityContext.setAuthentication(abstractAuthenticationToken);
			SecurityContextHolder.setContext(securityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}

		filterChain.doFilter(request, response); // 다음 필터로 요청 전달
	}

	// Authorization 헤더에서 Bearer 토큰을 파싱하는 메소드
	private String parseBearerToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
			return authorization.substring(7);
		}
		return null;
	}

	// 쿠키에서 accessToken을 추출하는 메소드
	private String extractTokenFromCookie(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (var cookie : request.getCookies()) {
				if ("accessToken".equals(cookie.getName())) { // 쿠키 이름이 "accessToken"인지 확인
					return cookie.getValue(); // 토큰 반환
				}
			}
		}
		return null; // 쿠키에 토큰이 없는 경우 null 반환
	}
}
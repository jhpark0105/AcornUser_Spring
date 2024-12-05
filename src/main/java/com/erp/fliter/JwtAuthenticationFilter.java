package com.erp.fliter;

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

import com.erp.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			// 토큰 검증
			String token = parseBearerToken(request);
			if(token == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			// 아이디 검증
			String id = jwtProvider.validate(token);
			if(id == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			
			AbstractAuthenticationToken abstractAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(id, null, AuthorityUtils.NO_AUTHORITIES); // 정보를 담는 객체 생성
			abstractAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 인증 요청에 대한 세부정보 설정
			
			// Context 등록
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 비어있는 콘텍스트 생성
			securityContext.setAuthentication(abstractAuthenticationToken);
			
			SecurityContextHolder.setContext(securityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		filterChain.doFilter(request, response);
	}
	
	// Barer 작업
	private String parseBearerToken(HttpServletRequest request) {
		
		String authorization = request.getHeader("Authorization");
		
		boolean hasAuthorization = StringUtils.hasText(authorization); // StringUtils.hasText() : 값이 없다면 false를 반환
		if(!hasAuthorization) return null;
		
		boolean isBearer = authorization.startsWith("Bearer ");
		if(!isBearer) return null;
		
		String token = authorization.substring(7);
		return token;
	}
}

package com.shop.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shop.fliter.JwtAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
				.csrf(csrf -> csrf.disable()) // CSRF 비활성화
				.httpBasic(httpBasic -> httpBasic.disable()) // 기본 인증 비활성화
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 상태 없음
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 프리플라이트 요청 허용
						.requestMatchers("/user/signup", "/user/login", "/", "/main/**", "/notice/**","/reservation/**").permitAll() // 인증 없이 허용
						.anyRequest().authenticated() // 그 외 요청은 인증 필요
				)
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(new FailedAuthenticationEntryPoint()) // 인증 실패 처리
				);

		// JWT 인증 필터 추가
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001")); // 허용할 React 서버 주소
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
		configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
		configuration.setAllowCredentials(true); // 인증 정보 포함 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

// 예외 처리 클래스
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("{\"code\": \"AF\", \"message\": \"Authorization Failed\"}");
	}
}

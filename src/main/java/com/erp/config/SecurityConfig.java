package com.erp.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.erp.fliter.JwtAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration // 스프링 설정 클래스
@EnableWebSecurity // 스프링 시큐리티 기능을 활성화
@RequiredArgsConstructor
public class SecurityConfig {
	// JWT 인증 필터인 JwtAuthenticationFilter 의존성 주입
	private final JwtAuthenticationFilter jwtAuthenticationFilter; 
	// SecurityFilterChain :
	// 스프링 시큐리티에서 HTTP 요청을 처리하는 필터 체인을 정의한다. 이 체인은 요청을 필터링하고, 요청에 대한 인증 및 인가를 수행하기 위해 설정
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// CORS 설정과 CSRF 비활성화, 세션 관리 정책 설정
		httpSecurity
			.cors().and() // CORS 설정 활성화
			.csrf().disable() // CSRF 보호 비활성화 (개발시 비활성화, 서비스 제공시 활성화 해야함)
			.httpBasic().disable() // HTTP Basic 인증 비활성화
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests() // 각각의 요청에 대한 권한 설정 시작
			.requestMatchers("/", "/login","/admin/login", "/signup", "/admin/signup", "/logoutProcess", "/main/**", "/main/signup", "/dashboard/**", "/main/dashboard/**").permitAll()
			.requestMatchers("/main/dashboard/**").permitAll()
			.anyRequest().permitAll().and() // 모든 요청을 허용 (개발 편의상 설정해놓음)
			.exceptionHandling().authenticationEntryPoint(new FailedAuthenticationEntryPoint()); // 인증 실패 시 처리 로직 설정
		
		// JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
		// UsernamePasswordAuthenticationFilter : 사용자 이름과 비밀번호를 사용하여 인증을 처리하는 필터. 이 필터는 로그인 요청을 처리하고, 사용자가 입력한 자격 증명을 검증
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build(); // 설정 완료 후 SecurityFilterChain 반환
    }
	
	// BCryptPasswordEncoder : 스프링 시큐리티에서 제공하는 패스워드 암호화 기능
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 인스턴스 반환
	}
}

// FailedAuthenticationEntryPoint: 예외 상황(인증 실패)에 발생한 이벤트 처리를 위한 클래스
// AuthenticationEntryPoint : 
// 스프링 시큐리티에서 인증이 필요한 리소스에 접근할 때 사용자가 인증되지 않았을 경우의 진입점을 정의하는 인터페이스, 인증 실패 시 어떤 응답을 반환할지를 결정한다.
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.setContentType("application/json"); // 응답 타입을 JSON으로 설정
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 상태 코드를 401로 설정
		response.getWriter().write("{\"code\": \"AF\", \"message\": \"Authorization Failed\"}"); // 예외 상황 발생 시 JSON 응답 작성
	}
}

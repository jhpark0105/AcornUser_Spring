package com.erp.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.erp.process.CustomUserDetailsProcess;

@Configuration
@EnableWebSecurity // 해당 클래스가 스프링 시큐리티에 의해 관리됨
public class SecurityConfig {
	
	@Autowired
    private CustomUserDetailsProcess customUserDetailsService;
		
	// SecurityFilterChain
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        // 인가, 로그인, 로그아웃 설정
        httpSecurity.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/", "/login", "/signup").permitAll()
//                .requestMatchers("/dashboard/**").hasAnyRole("BRANCH", "ADMIN") // 대시보드는 브랜치, 어드민 권한 접근 가능
//                .requestMatchers("/admin/**").hasRole("ADMIN") // 어드민 페이지는 어드민 권한만 접근 가능
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
        );

        httpSecurity.formLogin((auth) -> auth
                //.loginPage("/login")
                .loginProcessingUrl("/loginProcess")
                .usernameParameter("id")
                .defaultSuccessUrl("/loginSuccess")
                .permitAll()
        );

        httpSecurity.logout((auth) -> auth
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutSuccess")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        httpSecurity.cors(cors -> cors
        		.configurationSource(corsConfigurationSource())); // CORS 설정 적용
        
        httpSecurity.csrf(csrf -> csrf
        		.disable()); // CSRF 비활성화 (개발 환경에서만)
        
        return httpSecurity.build();
    }
	
	// BCryptPasswordEncoder : 스프링 시큐리티는 제공하는 패스워드 암호화 기능
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 허용할 출처
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 헤더
		configuration.setAllowCredentials(true); // 쿠키와 인증 정보를 포함할지 여부
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // 모든 URL에 적용
		return source;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailsService); // 위에서 작성한 서비스
		authProvider.setPasswordEncoder(bCryptPasswordEncoder()); // 비밀번호 암호화 처리
		return authProvider;
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }	
}

package com.erp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings (CorsRegistry corsRegistry) {
		corsRegistry
		.addMapping("/**")
		//.allowedOrigins("*")
		.allowedOrigins("http://localhost:3000") // React의 로컬 서버 주소
        .allowedMethods("*") // 허용할 HTTP 메서드
        .allowedHeaders("*") // 허용할 헤더
        .allowCredentials(true); // 인증 정보 포함 허용
	}
}

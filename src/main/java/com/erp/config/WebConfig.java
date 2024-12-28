package com.erp.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 공지사항 이미지 경로
        Path noticeUploadDir = Paths.get("./uploads/notice");
        String noticeUploadPath = noticeUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/uploads/notice/**")
                .addResourceLocations("file:" + noticeUploadPath + "/");

        // 상품 이미지 경로
        Path productUploadDir = Paths.get("./uploads/product");
        String productUploadPath = productUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/uploads/product/**")
                .addResourceLocations("file:" + productUploadPath + "/");
    }
}
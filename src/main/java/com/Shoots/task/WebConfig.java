package com.Shoots.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /userupload/** 요청을 C 드라이브의 c:/upload/ 디렉토리로 매핑
        registry.addResourceHandler("/userupload/**")
                .addResourceLocations("file:///c:/upload/");  // 절대 경로
    }
}

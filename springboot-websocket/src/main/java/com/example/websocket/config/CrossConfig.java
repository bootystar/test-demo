package com.example.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 添加跨域配置
 *
 * @author booty
 * @date 2021/5/21 09:52
 */
@Configuration
public class CrossConfig implements WebMvcConfigurer {
    static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //依次添加 请求路径映射，来源，凭证，方法
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods(ORIGINS)
                .maxAge(3600);
        /*
        不同框架中对一拦截所有请求配置有些不一样
        springmvc中认为 / 和 /* 表示所有url
        springboot、shiro等框架在发明时将 /**作为代表请求
         */
    }

    //在容器中创建bean对象，在WebSocketUtil中需要用到的RemoteEndpoint对象
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

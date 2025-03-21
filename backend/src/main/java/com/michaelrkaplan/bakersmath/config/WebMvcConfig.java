package com.michaelrkaplan.bakersmath.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        ConfigurableServletWebServerFactory factory = new TomcatServletWebServerFactory(); // Or any other factory
        factory.setMimeMappings(createMimeMappings());
        return factory;
    }

    private MimeMappings createMimeMappings() {
        MimeMappings mappings = new MimeMappings();
        mappings.add("js", "text/javascript");
//        mappings.add("js", "application/json");
        return mappings;
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggingInterceptor());
//    }


}

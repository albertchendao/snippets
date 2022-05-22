package org.example.user.config;

import org.example.user.interceptor.PrometheusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class WebMVConfig extends WebMvcConfigurerAdapter {

    @Bean
    PrometheusInterceptor prometheusInterceptor() {
        return new PrometheusInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(prometheusInterceptor())
                .addPathPatterns("/**");
    }
}

package dev.studentstay.Documente.jwt_security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtValidationFilter> jwtValidationFilter(GrpcClientService grpcClientService) {
        FilterRegistrationBean<JwtValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtValidationFilter(grpcClientService));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}

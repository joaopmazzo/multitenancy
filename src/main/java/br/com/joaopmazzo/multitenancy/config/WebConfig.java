package br.com.joaopmazzo.multitenancy.config;

import br.com.joaopmazzo.multitenancy.config.multitenancy.TenantRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantRequestInterceptor tenantRequestInterceptor;

    private static final String[] EXCLUDE_ALL_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/actuator/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantRequestInterceptor).addPathPatterns("/api/**").excludePathPatterns(EXCLUDE_ALL_LIST);
    }

}

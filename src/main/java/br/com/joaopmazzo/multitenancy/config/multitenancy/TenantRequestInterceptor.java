package br.com.joaopmazzo.multitenancy.config.multitenancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) return false;

        Jwt jwt = (Jwt) authentication.getPrincipal();

        return Optional
                .ofNullable(jwt.getClaims().get("azp"))
                .map(tenant -> {
                    TenantContext.setCurrentTenant(tenant.toString());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        TenantContext.clear();
    }

}
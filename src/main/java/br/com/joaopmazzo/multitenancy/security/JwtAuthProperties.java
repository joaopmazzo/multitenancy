package br.com.joaopmazzo.multitenancy.security;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "jwt.auth")
public record JwtAuthProperties(Map<String, Issuer> issuers) {

    public record Issuer(@NotNull String resourceId, @NotNull String principalAttribute, @NotNull String tenantId) {
    }
}
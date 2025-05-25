package br.com.joaopmazzo.multitenancy.config.multitenancy;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "tenants")
public record TenantsDataSourceProperties(Map<String, DataSourceConfig> configs) {

    public record DataSourceConfig(@NotNull String url, @NotNull String username, @NotNull String password, @NotNull String driverClassName) {
    }
}
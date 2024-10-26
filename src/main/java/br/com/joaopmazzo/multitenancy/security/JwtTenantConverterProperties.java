package br.com.joaopmazzo.multitenancy.security;

import lombok.Data;

@Data
public class JwtTenantConverterProperties {

    private String resourceId;
    private String principalAttribute;
    private String tenantId;

}
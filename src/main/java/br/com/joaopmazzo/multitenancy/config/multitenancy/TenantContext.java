package br.com.joaopmazzo.multitenancy.config.multitenancy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContext {

    public static final String DEFAULT_TENANT_ID = "";
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        log.debug("Setting tenantId to {}", tenant);
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }


}
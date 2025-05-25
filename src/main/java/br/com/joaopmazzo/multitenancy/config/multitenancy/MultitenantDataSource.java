package br.com.joaopmazzo.multitenancy.config.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected String determineCurrentLookupKey() {
        String tenant = TenantContext.getCurrentTenant();
        if (tenant == null) {
            throw new RuntimeException("Nenhum tenant configurado ou escolhido");
        }
        return tenant;
    }

}
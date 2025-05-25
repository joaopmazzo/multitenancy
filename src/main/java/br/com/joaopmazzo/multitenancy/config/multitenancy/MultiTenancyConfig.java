package br.com.joaopmazzo.multitenancy.config.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenancyConfig {

    @Bean
    public Map<String, DataSource> dataSources(TenantsDataSourceProperties tenantProps) {
        Map<String, DataSource> map = new HashMap<>();
        tenantProps.configs().forEach((tenant, cfg) -> {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(cfg.url());
            ds.setUsername(cfg.username());
            ds.setPassword(cfg.password());
            ds.setDriverClassName(cfg.driverClassName());
            ds.setMinimumIdle(2);
            ds.setMaximumPoolSize(5);
            map.put(tenant, ds);
        });
        return map;
    }

    @Bean
    public DataSource dataSource(Map<String, DataSource> dataSources, TenantsDataSourceProperties tenantProps) {
        Map<Object, Object> resolvedDataSources = new HashMap<>(dataSources);
//        String defaultTenant = tenantProps.configs().keySet().stream().findFirst()
//                .orElseThrow(() -> new RuntimeException("Nenhum tenant configurado"));

        AbstractRoutingDataSource dataSource = new MultitenantDataSource();
//        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));a
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();
        return dataSource;
    }

}

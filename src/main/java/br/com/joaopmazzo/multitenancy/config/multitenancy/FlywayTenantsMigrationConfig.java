package br.com.joaopmazzo.multitenancy.config.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayTenantsMigrationConfig {

    private final TenantsDataSourceProperties tenantsDataSourceProperties;

    public FlywayTenantsMigrationConfig(TenantsDataSourceProperties tenantsDataSourceProperties) {
        this.tenantsDataSourceProperties = tenantsDataSourceProperties;
    }

    @PostConstruct
    public void migrateAllTenants() {
        tenantsDataSourceProperties.configs().forEach((tenant, config) -> {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(config.url());
            dataSource.setUsername(config.username());
            dataSource.setPassword(config.password());
            dataSource.setDriverClassName(config.driverClassName());

            Flyway.configure()
                    .locations("db/migration")
                    .baselineOnMigrate(true)
                    .schemas("public")
                    .dataSource(dataSource)
                    .load()
                    .migrate();
        });
    }

}

package com.bizremark.institution.common.configuration;


import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;

public class FlywayMigration {
    @Value("${flyway.url:}")
    private String url;

    @Value("${flyway.user:}")
    private String user;

    @Value("${flyway.password:}")
    private String password;

    @Value("${flyway.enabled:false}")
    private Boolean enabled;

    @Value("${flyway.baseline-on-migrate:false}")
    private Boolean baseLineOnMigration;

    /**
     * Executes flyway migration.
     */
    public void migrate() {
        if (enabled) {
            Flyway flyway = Flyway
                    .configure()
                    .dataSource(url, user, password)
                    .baselineOnMigrate(baseLineOnMigration)
                    .outOfOrder(true)
                    .load();
            flyway.migrate();
        }
    }
}

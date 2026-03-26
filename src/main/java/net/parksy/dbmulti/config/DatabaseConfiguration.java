package net.parksy.dbmulti.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("net.parksy.dbmulti")
@Slf4j
public class DatabaseConfiguration {

    @Primary
    @Bean //(name = "primaryDataSourceProperties")
    @ConfigurationProperties("first.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean //(name = "primaryDataSource")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(ClientInfoStatusDataSource.class).build();
    }

    @Bean(name = "reportingDataSourceProperties")
    @ConfigurationProperties("datasources.reporting")
    public DataSourceProperties reportingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "reportingDataSource")
    public DataSource reportingDataSource(DataSourceProperties properties) {
        log.info("jdbc url: {}", properties.determineUrl());
        return properties.initializeDataSourceBuilder().type(ClientInfoStatusDataSource.class).build();
    }

}

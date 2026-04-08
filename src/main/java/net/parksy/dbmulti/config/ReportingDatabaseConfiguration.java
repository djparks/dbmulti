package net.parksy.dbmulti.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = DatabaseConfiguration.BASE_PACKAGE,
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ReportingRepository.class),
        entityManagerFactoryRef = "reportingEntityManagerFactory",
        transactionManagerRef = "reportingTransactionManager"
)
@Slf4j
public class ReportingDatabaseConfiguration {

    @Bean(name = "reportJpaProperties")
    @ConfigurationProperties("spring.jpa.hibernate")
    public Map<String, String> reportJpaProperties() {
        return new HashMap<>();
    }

    @Bean(name = "reportingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reportingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("reportingDataSource") DataSource dataSource,
            @Qualifier("reportJpaProperties") Map<String, String> jpaProperties) {
        return builder
                .dataSource(dataSource)
                .packages(DatabaseConfiguration.BASE_PACKAGE)
                .persistenceUnit("reporting")
                .properties(jpaProperties)
                .build();
    }

    @Bean(name = "reportingTransactionManager")
    public PlatformTransactionManager reportingTransactionManager(
            @Qualifier("reportingEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "reportingDataSourceProperties")
    @ConfigurationProperties("datasources.reporting")
    public DataSourceProperties reportingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "reportingDataSource")
    public DataSource reportingDataSource(@Qualifier("reportingDataSourceProperties") DataSourceProperties properties) {
        log.info("jdbc url: {}", properties.determineUrl());
        return properties.initializeDataSourceBuilder().type(ClientInfoStatusDataSource.class).build();
    }
}

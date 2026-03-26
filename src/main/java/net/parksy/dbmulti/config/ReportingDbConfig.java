package net.parksy.dbmulti.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "net.parksy.dbmulti",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ReportingRepository.class),
        entityManagerFactoryRef = "reportingEntityManagerFactory",
        transactionManagerRef = "reportingTransactionManager"
)
public class ReportingDbConfig {

    @Bean(name = "reportingDataSourceProperties")
    @ConfigurationProperties("datasources.reporting")
    public DataSourceProperties reportingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "reportingDataSource")
    public DataSource reportingDataSource() {
        return reportingDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "reportingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reportingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("reportingDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("net.parksy.dbmulti")
                .persistenceUnit("reporting")
                .build();
    }

    @Bean(name = "reportingTransactionManager")
    public PlatformTransactionManager reportingTransactionManager(
            @Qualifier("reportingEntityManagerFactory") LocalContainerEntityManagerFactoryBean reportingEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(reportingEntityManagerFactory.getObject()));
    }

    @Bean(name = "reportingEntityManager")
    public EntityManager reportingEntityManager(
            @Qualifier("reportingEntityManagerFactory") LocalContainerEntityManagerFactoryBean reportingEntityManagerFactory) {
        return Objects.requireNonNull(reportingEntityManagerFactory.getObject()).createEntityManager();
    }
}

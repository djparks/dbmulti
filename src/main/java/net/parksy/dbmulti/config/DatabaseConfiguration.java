package net.parksy.dbmulti.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = DatabaseConfiguration.BASE_PACKAGE,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ReportingRepository.class),
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@Slf4j
public class DatabaseConfiguration {
    public static final String BASE_PACKAGE = "net.parksy.dbmulti";

    @Primary
    @Bean
    @ConfigurationProperties("spring.jpa")
    public JpaProperties firstJpaProperties() {
        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.getProperties().put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        jpaProperties.getProperties().put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        log.info("jpa properties: {}", jpaProperties.getProperties());
        return jpaProperties;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource,
            JpaProperties jpaProperties
    ) {
        return builder
                .dataSource(dataSource)
                .packages(BASE_PACKAGE)
                .persistenceUnit("primary")
                .properties(jpaProperties.getProperties())
                .build();
    }

//    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        return new EntityManagerFactoryBuilder(
//                vendorAdapter,
//                jpaProperties.getProperties(),
//                null
//        );
//    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

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



}

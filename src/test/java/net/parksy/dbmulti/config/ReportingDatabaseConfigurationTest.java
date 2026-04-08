package net.parksy.dbmulti.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReportingDatabaseConfigurationTest {

    @Configuration
    @EnableConfigurationProperties
    static class TestConfig {
        @Bean(name = "reportJpaProperties")
        @ConfigurationProperties("spring.jpa.hibernate")
        public Map<String, String> reportJpaProperties() {
            return new HashMap<>();
        }
    }

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfig.class);

    @Test
    void reportJpaProperties_shouldReadNamingStrategiesFromSpringJpaHibernate() {
        this.contextRunner
                .withPropertyValues(
                        "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                        "spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.SpringImplicitNamingStrategy"
                )
                .run(context -> {
                    assertThat(context).hasBean("reportJpaProperties");
                    @SuppressWarnings("unchecked")
                    Map<String, String> jpaProperties = context.getBean("reportJpaProperties", Map.class);

                    assertThat(jpaProperties)
                            .containsEntry("naming.physical-strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy")
                            .containsEntry("naming.implicit-strategy", "org.hibernate.boot.model.naming.SpringImplicitNamingStrategy");
                });
    }
}

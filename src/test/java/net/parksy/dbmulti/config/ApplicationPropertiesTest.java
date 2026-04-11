package net.parksy.dbmulti.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationPropertiesTest {

    @Configuration
    @EnableConfigurationProperties(ApplicationProperties.class)
    static class TestConfig {
    }

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfig.class);

    @Test
    void applicationProperties_shouldReadNamingStrategies() {
        this.contextRunner
                .withPropertyValues(
                        "first.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
                        "first.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.SpringImplicitNamingStrategy"
                )
                .run(context -> {
                    ApplicationProperties properties = context.getBean(ApplicationProperties.class);
                    assertThat(properties.getHibernate().getNaming().getPhysicalStrategy())
                            .isEqualTo("org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
                    assertThat(properties.getHibernate().getNaming().getImplicitStrategy())
                            .isEqualTo("org.hibernate.boot.model.naming.SpringImplicitNamingStrategy");
                });
    }
}

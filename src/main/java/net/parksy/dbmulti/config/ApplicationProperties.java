package net.parksy.dbmulti.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "first", ignoreUnknownFields = false)
public class ApplicationProperties {
    private Datasource datasource = new Datasource();

    @Data
    public static class Datasource {
        private String url;
        private String driverClassName;
        private String username;
        private String password;
    }
}

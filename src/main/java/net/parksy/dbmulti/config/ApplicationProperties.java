package net.parksy.dbmulti.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "first", ignoreUnknownFields = false)
public class ApplicationProperties {
}

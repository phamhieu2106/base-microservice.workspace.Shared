package com.henry.config;

import jakarta.annotation.PostConstruct;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class GradlePropertiesLoader {
    private final ConfigurableEnvironment environment;

    public GradlePropertiesLoader(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadGradleProperties() {
        try {
            File propertiesFile = new File(new File("").getAbsolutePath(), "gradle.properties");

            if (!propertiesFile.exists()) {
                throw new RuntimeException("gradle.properties file not found at: " + propertiesFile.getAbsolutePath());
            }

            Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesFile));
            PropertiesPropertySource propertySource = new PropertiesPropertySource("gradleProperties", properties);
            environment.getPropertySources().addLast(propertySource);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load gradle.properties", e);
        }
    }
}
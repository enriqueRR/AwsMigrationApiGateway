package com.uttara.example.AwsMigrationApiGateway.config;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.LDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LaunchDarklyConfig {

    @Value("${LAUNCH_DARKLY_SDK_KEY}")
    String ldSdkKey;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean(name = "ldUser")
    public LDUser getLDUser() {
        return new LDUser("AwsMigrationApiGateway");
    }

    @Bean(name = "ldClient")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LDClient getLaunchdarklyClient() {
        if (ldSdkKey == null) {
            logger.info("LaunchDarklyClient sdk key not provided; client will be initialized; but only default values will be served;");
        }
        logger.info("Inside getLaunchdarklyClient");
        return new LDClient(ldSdkKey);
    }
}
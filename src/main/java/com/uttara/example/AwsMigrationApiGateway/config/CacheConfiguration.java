package com.uttara.example.AwsMigrationApiGateway.config;

import com.uttara.example.AwsMigrationApiGateway.filter.DeviceIdFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    CacheManagerCustomizer<ConcurrentMapCacheManager> customizer() {
        return new ConcurrentCustomizer();
    }

    private class ConcurrentCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

        final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void customize(ConcurrentMapCacheManager cacheManager) {
            cacheManager.setAllowNullValues(false);
            logger.info("cache customizer invoked");
        }
    }
}

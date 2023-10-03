package com.uttara.example.AwsMigrationApiGateway.service;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.LDClient;
import org.springframework.stereotype.Service;

@Service
public class LaunchDarklyClient {

    private final LDClient ldClient;
    private final LDUser ldUser;

    public LaunchDarklyClient(LDClient ldClient, LDUser ldUser) {
        this.ldClient = ldClient;
        this.ldUser = ldUser;
    }

    public boolean getFlagValueNgdc() {
        return ldClient.boolVariation("route-to-ngdc", ldUser, false);
    }

    public boolean getFlagValueAws() {
        return ldClient.boolVariation("route-to-aws", ldUser, false);
    }
}
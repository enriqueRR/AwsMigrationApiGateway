package com.uttara.example.AwsMigrationApiGateway.service;


import com.uttara.example.AwsMigrationApiGateway.common.Shard;
import com.uttara.example.AwsMigrationApiGateway.entity.Device;
import com.uttara.example.AwsMigrationApiGateway.repository.Gen1DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class Gen1DeviceService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Gen1DeviceRepository gen1DeviceRepository;

    @Cacheable(cacheNames = "deviceId")
    public String getDevices(String deviceId) {
        logger.info("methodName: getDevices");
        Device device = gen1DeviceRepository.findByDeviceId(deviceId);
        String shardCode = null;
        if (device != null) {
            shardCode = device.getShard().getCode() + "/" + device.getShard().getHostname();
        }
        return shardCode;
    }

    @Cacheable(cacheNames = "deviceEmailId")
    public String getDeviceEmailId(String deviceEmailId) {
        logger.info("methodName: getDeviceEmailId");
        Device device = gen1DeviceRepository.findDeviceByDeviceEmailId(deviceEmailId);
        String shardCode = null;
        if (device != null) {
            shardCode = device.getShard().getCode() + "/" + device.getShard().getHostname();
        }
        return shardCode;
    }

    @Cacheable(cacheNames = "code")
    public String getHostNameByShardCode(String code) {
        logger.info("methodName: getHostNameByShardCode");
        Shard shard = gen1DeviceRepository.findHostNameByShardCode(code.substring(0, 3));
        String shardHostName = null;
        if (shard != null) {
            shardHostName = shard.getCode() + "/" + shard.getHostname();
        }
        return shardHostName;
    }

    @Cacheable(cacheNames = "awsRoute")
    public String findAwsRouteUri(String apiEndPointName) {
        logger.info("methodName: findAwsRouteUri");
        return gen1DeviceRepository.findAwsRouteUri(apiEndPointName);
    }

    @Cacheable(cacheNames = "ngdcRoute")
    public String findNgdcRouteUri(String apiEndPointName) {
        logger.info("methodName: findNgdcRouteUri");
        return gen1DeviceRepository.findNgdcRouteUri(apiEndPointName);
    }


}

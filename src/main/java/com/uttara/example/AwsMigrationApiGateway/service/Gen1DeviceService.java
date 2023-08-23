package com.uttara.example.AwsMigrationApiGateway.service;


import com.uttara.example.AwsMigrationApiGateway.entity.Device;

import com.uttara.example.AwsMigrationApiGateway.repository.Gen1DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class Gen1DeviceService {


    @Autowired
    private Gen1DeviceRepository gen1DeviceRepository;

    public String getDevices(String deviceId) {
        Device device = gen1DeviceRepository.findByDeviceId(deviceId);
        String shardCode=null;
        if(device!=null) {
            shardCode = device.getShard().getCode()+"/"+device.getShard().getHostname();
        }
        return shardCode;
   }
    public String getDeviceEmailId(String deviceEmailId) {
        Device device = gen1DeviceRepository.findDeviceByDeviceEmailId(deviceEmailId);
        String shardCode=null;
        if(device!=null) {
            shardCode = device.getShard().getCode()+"/"+device.getShard().getHostname();
            }
       return shardCode;
    }
    public String getHostNameByShardCode(String code) {
        Device device = gen1DeviceRepository.findHostNameByShardCode(code.substring(0,3));
        String shardCode=null;
        if(device!=null) {
            shardCode = device.getShard().getCode()+"/"+device.getShard().getHostname();
        }
        return shardCode;
    }




}

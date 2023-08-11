/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.common;



import com.uttara.example.AwsMigrationApiGateway.entity.MigrationStatus;

import java.sql.Timestamp;

/**
 * @author singkarm
 *
 */
public interface DeviceData {
	

	public String getDeviceCrypticEmailId();
	public String getDeviceId();
	public Shard getShard();

    Timestamp getLastUsed();

    public String getModelNumber();
	public String getSerialNumber();	
	
	public MigrationStatus getMigrationStatus();
}

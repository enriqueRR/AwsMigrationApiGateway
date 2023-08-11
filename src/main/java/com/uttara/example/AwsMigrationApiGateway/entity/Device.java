package com.uttara.example.AwsMigrationApiGateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.uttara.example.AwsMigrationApiGateway.common.DeviceData;
import com.uttara.example.AwsMigrationApiGateway.common.Shard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;



//TODO, After 6/1 upgrade put these values...@UniqueConstraint(columnNames={"modelNumber","serialNumber"})
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames="deviceEmailId")})
public class Device implements DeviceData
{
	
	private static final Logger LOG = LoggerFactory.getLogger(Device.class);
	 
	@Id @Column(name="deviceId",unique=true)
	private String deviceId;
	@Column(name="deviceEmailId", nullable=false)
	private String deviceEmailId;
	@Column(name="lastUsed")
	private Timestamp lastUsed;
	
	@Column(name="modelNumber" )
	private String modelNumber;
	
	@Column(name="serialNumber")
	private String serialNumber;
		
	@OneToMany(cascade = {CascadeType.ALL},fetch=FetchType.EAGER, mappedBy = "device")
//	@JoinColumn(name = "deviceId")
	private Set<DeviceEmailAddressAlias> deviceEmailAddressAliases;
	
	@OneToOne(cascade = {CascadeType.ALL}, mappedBy="device")
	@JoinColumn(name = "deviceId", updatable=false, insertable=false)
//added
	@JsonIgnore
	private DeviceExtension deviceExtension;
	
	@ManyToOne
	@JoinColumn(name="code", nullable=false)
//added
	@JsonIgnore
	private ShardImpl shard;
	
	@Enumerated(EnumType.STRING)
	private MigrationStatus migrationStatus;
	
	@Override
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Override
	public String getDeviceCrypticEmailId() {
		return deviceEmailId;
	}
	public void setDeviceCrypticEmailId(String deviceEmailId) {
		this.deviceEmailId = deviceEmailId;
	}
	@Override
	public Shard
	getShard() {
		return shard;
	}
	public void setShard(Shard shard) {
		this.shard = (ShardImpl) shard;
	}
	@Override 
	public Timestamp getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	@Override
	public String getModelNumber() {
		return modelNumber;
}
	public void setModelNumber(String modelNumberParm) {
		this.modelNumber = modelNumberParm;
	}
	
	@Override
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumberParm) {
		this.serialNumber = serialNumberParm;
	}

	/**
	 * Utility method to get the active email address alias
	 * 
	 * @return String
	*/

	public String getDeviceActiveEmailAddress() {
		Set<DeviceEmailAddressAlias> aliases = getDeviceEmailAddressAliases();
		if(aliases!=null && aliases.size()>0){
			// Right now there can only be 1 active email address alias which will have its LockOutPeriodStartTimeInMilliSec as null
			for(DeviceEmailAddressAlias d: aliases){
				if(d.getLockOutPeriodStartTimeInMilliSec()==null){
			if(LOG.isDebugEnabled()){
						LOG.debug("active email address is infact an alias and is :"+d.getEmailAddressAlias());
			}
					return d.getEmailAddressAlias();
		}
			}
			if(LOG.isDebugEnabled()){
				LOG.debug("all the aliases are disabled, hence returning the original cryptic email address of the printer which is :"+getDeviceCrypticEmailId());
			}
			return getDeviceCrypticEmailId();		
		}
			if(LOG.isDebugEnabled()){
				LOG.debug("active email address is the original cryptic email address of the printer and is :"+getDeviceCrypticEmailId());
			}
			return getDeviceCrypticEmailId();			
		}
	public Set<DeviceEmailAddressAlias> getDeviceEmailAddressAliases() {
		return deviceEmailAddressAliases;
	}
	public void setDeviceEmailAddressAliases(
			Set<DeviceEmailAddressAlias> deviceEmailAddressAliases) {
		this.deviceEmailAddressAliases = deviceEmailAddressAliases;
	}
	public MigrationStatus getMigrationStatus() {
		return migrationStatus;
	}
	public void setMigrationStatus(MigrationStatus migrationStatus) {
		this.migrationStatus = migrationStatus;
	}
		
	public DeviceExtension getDeviceExtension() {
		return deviceExtension;
	}
	public void setDeviceExtension(DeviceExtension deviceExtension) {
		this.deviceExtension = deviceExtension;
	}
	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", deviceEmailId=" + deviceEmailId + ", lastUsed=" + lastUsed
				+ ", modelNumber=" + modelNumber + ", serialNumber=" + serialNumber + ", deviceEmailAddressAliases="
				+ deviceEmailAddressAliases + ", deviceExtension=" + deviceExtension + ", shard=" + shard
				+ ", migrationStatus=" + migrationStatus + "]";
	}	
	
	
	
}
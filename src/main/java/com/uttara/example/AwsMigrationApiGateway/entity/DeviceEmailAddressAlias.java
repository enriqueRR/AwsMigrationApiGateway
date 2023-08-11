package com.uttara.example.AwsMigrationApiGateway.entity;

import javax.persistence.*;

@Entity
@Table(name = "DeviceEmailAddressAlias", uniqueConstraints=@UniqueConstraint(columnNames={"deviceId","lockOutPeriodStartTimeInMilliSec"}))

public class DeviceEmailAddressAlias {

	@Id
	@Column(name = "emailAddressAlias", length = 255,nullable = false)
	// primary key, this column is for storage of device Active Aliases
	// each device can have multiple aliases, out of which only one can be active in phase-1
	private String emailAddressAlias;

	@ManyToOne
	@JoinColumn(name = "deviceId", unique=false, updatable=false)
	private Device device;
	
	@Column(name = "creationTimeInMilliSec",nullable = false)
	private Long creationTimeInMilliSec;

	@Column(name = "lockOutPeriodStartTimeInMilliSec",nullable = true)
	private Long lockOutPeriodStartTimeInMilliSec;
	
	public String getEmailAddressAlias() {
		return emailAddressAlias;
	}

	public void setEmailAddressAlias(String emailAddressAlias) {
		this.emailAddressAlias = emailAddressAlias;
	}

	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	
	public long getCreationTimeInMilliSec() {
		return creationTimeInMilliSec;
	}

	public void setCreationTimeInMilliSec(Long creationTimeInMilliSec) {
		this.creationTimeInMilliSec = creationTimeInMilliSec;
	}

	public Long getLockOutPeriodStartTimeInMilliSec() {
		if(lockOutPeriodStartTimeInMilliSec!=null && lockOutPeriodStartTimeInMilliSec==0L){
			return null;
		}
		return lockOutPeriodStartTimeInMilliSec;
	}

	public void setLockOutPeriodStartTimeInMilliSec(Long lockOutPeriodStartTimeInMilliSec) {
		if(lockOutPeriodStartTimeInMilliSec==null){
			this.lockOutPeriodStartTimeInMilliSec=0L;
		}else{
			this.lockOutPeriodStartTimeInMilliSec = lockOutPeriodStartTimeInMilliSec;
		}
	}

}
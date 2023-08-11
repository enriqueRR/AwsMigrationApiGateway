/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.entity;

import javax.persistence.*;

@Entity
@Table(name = "DeviceExtension")
public class DeviceExtension {

	@Id
	@GeneratedValue
	private long id;

	@OneToOne
	@JoinColumn(name = "deviceId", updatable = false)
	private Device device;

	@Column(name = "deviceAcumenId")
	private String deviceAcumenId;

	@Column(name = "registration_id")
	private String registrationID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getDeviceAcumenId() {
		return deviceAcumenId;
	}

	public void setDeviceAcumenId(String deviceAcumenId) {
		this.deviceAcumenId = deviceAcumenId;
	}

	public String getRegistrationID() {
		return registrationID;
	}

	public void setRegistrationID(String registrationID) {
		this.registrationID = registrationID;
	}

}

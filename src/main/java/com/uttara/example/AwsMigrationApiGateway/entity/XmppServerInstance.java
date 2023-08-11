/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.entity;

import javax.persistence.*;




/**
 * @author singkarm
 *
 */
@Entity
public class XmppServerInstance {

	@Id
	@GeneratedValue
	private int instanceId;
	private String hostName;
	private String ports;
	
	
	@ManyToOne
	@JoinColumn(name="groupId")
	private XmppServerGroup xmppServerGroup;

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public XmppServerGroup getXmppServerGroup() {
		return xmppServerGroup;
	}

	public void setXmppServerGroup(XmppServerGroup xmppServerGroup) {
		this.xmppServerGroup = xmppServerGroup;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("hostname: ");
		builder.append(hostName);
		builder.append(" groupId");
		builder.append(xmppServerGroup.getGroupId());
		return builder.toString();
	}
	

	
}

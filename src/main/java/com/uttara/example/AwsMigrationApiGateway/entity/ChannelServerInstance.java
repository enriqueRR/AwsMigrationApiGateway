/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.entity;

import javax.persistence.*;




/**
 * @author lnand
 *
 */
@Entity
public class ChannelServerInstance {

	@Id
	@GeneratedValue
	private int instanceId;
	private String hostName;
	
	
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

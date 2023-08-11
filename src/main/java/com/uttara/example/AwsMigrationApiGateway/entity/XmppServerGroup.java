/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;


/**
 * @author singkarm
 *
 */
@Entity
public class XmppServerGroup {
	
	@Id
	@GeneratedValue
	private int groupId;
	private String lbHostName;
	
	/*@OneToOne @PrimaryKeyJoinColumn
	ShardImpl shard;*/
	private String dbHostname;
	private Integer port;
	private String databasename;
	private String userName;
	private String password;
	private Integer maxConnectionPerHost;
	private Integer idleConnectionPerHost;
	public Integer maxIdleTime ;	
	public String driverClass;
	public Integer idleConnectionTestPeriod;
	public String jdbcUrl;
	public String preferredTestQuery;
	private String channel_end_point;
	private Integer maxIdleTimeExcessConnections;
	private Integer checkoutTimeout;
	
	private Integer acquireIncrement;
	private Integer acquireRetryAttempts;
	private Integer acquireRetryDelay;
	private Integer maxStatements;
	private Integer numHelperThreads;
	private Boolean testConnectionOnCheckout;
	private Boolean testConnectionOnCheckin;
	private String mcs_channel_end_point;

	@Column (name="properties", length=5048)
	private HashMap<String, String> properties;
	
	@OneToMany(mappedBy = "xmppServerGroup")
	private Collection<XmppServerInstance> xmppServerInstances;
	
	@OneToMany(mappedBy = "xmppServerGroup")
	private Collection<ChannelServerInstance> channelServerInstances;
	
    
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxConnectionPerHost() {
		return maxConnectionPerHost;
	}

	public void setMaxConnectionPerHost(Integer maxConnectionPerHost) {
		this.maxConnectionPerHost = maxConnectionPerHost;
	}

	public Integer getIdleConnectionPerHost() {
		return idleConnectionPerHost;
	}

	public void setIdleConnectionPerHost(Integer idleConnectionPerHost) {
		this.idleConnectionPerHost = idleConnectionPerHost;
	}

	public Integer getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(Integer maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public Integer getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public void setIdleConnectionTestPeriod(Integer idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getPreferredTestQuery() {
		return preferredTestQuery;
	}

	public void setPreferredTestQuery(String preferredTestQuery) {
		this.preferredTestQuery = preferredTestQuery;
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public Collection<XmppServerInstance> getXmppServerInstances() {
		return xmppServerInstances;
	}

	public void setXmppServerInstances(
			Collection<XmppServerInstance> xmppServerInstances) {
		this.xmppServerInstances = xmppServerInstances;
	}
	
	public Collection<ChannelServerInstance> getChannelServerInstances() {
		return channelServerInstances;
	}

	public void setChannelServerInstances(
			Collection<ChannelServerInstance> channelServerInstances) {
		this.channelServerInstances = channelServerInstances;
	}

	public String getLbHostName() {
		return lbHostName;
	}

	public void setLbHostName(String lbHostName) {
		this.lbHostName = lbHostName;
	}

	public String getDbHostname() {
		return dbHostname;
	}

	public void setDbHostname(String dbHostname) {
		this.dbHostname = dbHostname;
	}

	public String getChannelEndPoint() {
		return channel_end_point;
	}

	public void setChannelEndPoint(String channelEndPoint) {
		this.channel_end_point = channelEndPoint;
	}
	
	public Integer getMaxIdleTimeExcessConnections() {
		return maxIdleTimeExcessConnections;
	}

	public void setMaxIdleTimeExcessConnections(Integer maxIdleTimeExcessConnections) {
		this.maxIdleTimeExcessConnections = maxIdleTimeExcessConnections;
	}

	public Integer getCheckoutTimeout() {
		return checkoutTimeout;
	}

	public void setCheckoutTimeout(Integer checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}
	
	public Integer getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(Integer acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public Integer getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public void setAcquireRetryAttempts(Integer acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public Integer getAcquireRetryDelay() {
		return acquireRetryDelay;
	}

	public void setAcquireRetryDelay(Integer acquireRetryDelay) {
		this.acquireRetryDelay = acquireRetryDelay;
	}

	public Integer getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(Integer maxStatements) {
		this.maxStatements = maxStatements;
	}

	public Integer getNumHelperThreads() {
		return numHelperThreads;
	}

	public void setNumHelperThreads(Integer numHelperThreads) {
		this.numHelperThreads = numHelperThreads;
	}

	public Boolean getTestConnectionOnCheckout() {
		return testConnectionOnCheckout;
	}

	public void setTestConnectionOnCheckout(Boolean testConnectionOnCheckout) {
		this.testConnectionOnCheckout = testConnectionOnCheckout;
	}

	public Boolean getTestConnectionOnCheckin() {
		return testConnectionOnCheckin;
	}

	public void setTestConnectionOnCheckin(Boolean testConnectionOnCheckin) {
		this.testConnectionOnCheckin = testConnectionOnCheckin;
	}

	public String getMcs_channel_end_point() {
		return mcs_channel_end_point;
	}

	public void setMcs_channel_end_point(String mcs_channel_end_point) {
		this.mcs_channel_end_point = mcs_channel_end_point;
	}
	
	
	/*public ShardImpl getShard() {
		return shard;
	}

	public void setShard(ShardImpl shard) {
		this.shard = shard;
	}*/

}

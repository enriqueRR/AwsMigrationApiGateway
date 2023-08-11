/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.common;



import com.uttara.example.AwsMigrationApiGateway.entity.XmppServerGroup;

import java.util.Map;

/**
 * @author singkarm
 *
 */
public interface Shard {
	
	public Integer getShardId();
	public String getCode() ;
	public String getHostname();
	public Integer getPort() ;
	public String getDatabasename();
	public String getUserName() ;
	public String getPassword();
	public Boolean getIsPasswordEncrypted();
	public Integer getMaxConnectionPerHost();
	public Integer getIdleConnectionPerHost();
	public Integer getMaxIdleTime() ;
	public String getType() ;
	public String getRegion();
	public Integer getLoadFactor();
	public long getDeviceCounter() ;
	public String getDriverClass();
	public Integer getIdleConnectionTestPeriod();
	public String getJdbcUrl();
	public XmppServerGroup getXmppServerGroup();
	public String getPreferredTestQuery();
    public void incrementDeviceCounter();
    public void decrementDeviceCounter();
    public Map<String, String> getProperties();
    /**
     * Returns the maximum idle time in seconds for the excess connections in the connection pool. 
     * @return
     */
	public Integer getMaxIdleTimeExcessConnections();
	
	/**
	 * Returns the checkout timeout in milliseconds for the incoming connection request. 
	 * @return
	 */
	public Integer getCheckoutTimeout();
	
	/**
	 * If testConnectionOnCheckin is true, an operation will be performed asynchronously at every connection checkin 
	 * to verify that the connection is valid. Use in combination with idleConnectionTestPeriod for quite reliable, always 
	 * asynchronous Connection testing. 
	 * 
	 * @return
	 */
	public Boolean getTestConnectionOnCheckin();
	
	/**
	 * If testConnectionOnCheckout is true, an operation will be performed at
	 * every connection checkout to verify that the connection is valid.
	 * @return
	 */
	public Boolean getTestConnectionOnCheckout();
	
	/**
	 * The field numHelperThreads defines the number of helper threads that generally performs the slow JDBC operations.
	 * @return
	 */
	public Integer getNumHelperThreads();
	
	/**
	 * The field maxStatements holds the size of c3p0's global PreparedStatement cache.
	 * @return
	 */
	public Integer getMaxStatements();
	
	/**
	 * The field acquireIncrement determines how many connections at a time c3p0 will try to acquire when the pool is exhausted. 
	 * @return
	 */
	public Integer getAcquireIncrement();
	
	/**
	 * The field acquireRetryDelay defines the time in milliseconds the C3P0 will wait between acquire attempts.
	 * @return
	 */
	public Integer getAcquireRetryDelay();
	
	/**
	 * The field acquireRetryAttempts defines how many times c3P0 will try to acquire a new connection.
	 * @return
	 */
	public Integer getAcquireRetryAttempts();

	/**
	 * Indicates if the shard is open for registration or not
	 * @return
	 */
	public boolean isOpenForRegistration();
}

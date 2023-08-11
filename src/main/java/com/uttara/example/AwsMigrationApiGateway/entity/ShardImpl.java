/**
 * 
 */
package com.uttara.example.AwsMigrationApiGateway.entity;



import com.uttara.example.AwsMigrationApiGateway.common.Shard;

import javax.persistence.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author singkarm
 *
 */
@Entity
@Table(name="Shard",uniqueConstraints=@UniqueConstraint(columnNames="code"))
public class ShardImpl implements Shard
{

	@Id
	@GeneratedValue
	@Column(name = "shardId")
	private int shardId;
	@Column(name ="code")
	private String code;
	private String hostname;
	private Integer port;
	private String databasename;
	private String userName;
	private String password;
	private Boolean isPasswordEncrypted;
	private Integer maxConnectionPerHost;
	private Integer idleConnectionPerHost;
	private Integer maxIdleTime;
	private String type;
	private String region;
	private Integer loadFactor;
	private String preferredTestQuery;
	private Integer idleConnectionTestPeriod;
	private String jdbcUrl;
	private String driverClass;
	/**
	 * The field maxIdleTimeExcessConnections helps us to configure the maximum timeout value for the excess connections which are idle.
	 */
	private Integer maxIdleTimeExcessConnections;
	
	/**
	 * The field checkoutTimeout helps us to configure the timeout value for the incoming getConnection request when the connection pool is exhausted.
	 */
	private Integer checkoutTimeout;
	
	/**
	 * The field acquireIncrement determines how many connections at a time c3p0 will try to acquire when the pool is exhausted.  
	 */
	@Column(columnDefinition="TINYINT(1) DEFAULT 3")
	private Integer acquireIncrement;
	
	/**
	 * The field acquireRetryAttempts defines how many times c3P0 will try to acquire a new connection.
	 */
	@Column(columnDefinition="INT(4) DEFAULT 30")
	private Integer acquireRetryAttempts;
	
	/**
	 * The field acquireRetryDelay defines the time in milliseconds the C3P0 will wait between acquire attempts.
	 */
	@Column(columnDefinition="INT(11) DEFAULT 1000")
	private Integer	acquireRetryDelay;
	
	/**
	 * The field maxStatements holds the size of c3p0's global PreparedStatement cache.
	 */
	@Column(columnDefinition="INT(4) DEFAULT 100")
	private Integer maxStatements;
	
	/**
	 * The field numHelperThreads defines the number of helper threads that generally performs the slow JDBC operations.
	 */
	@Column(columnDefinition="TINYINT(1) DEFAULT 3")
	private Integer numHelperThreads;
	
	/**
	 * If testConnectionOnCheckout is true, an operation will be performed at
	 * every connection checkout to verify that the connection is valid.
	 */
	@Column(columnDefinition="TINYINT(1) DEFAULT FALSE")
	private Boolean testConnectionOnCheckout;
	
	/**
	 * If testConnectionOnCheckin is true, an operation will be performed
	 * asynchronously at every connection checkin to verify that the connection
	 * is valid. Use in combination with idleConnectionTestPeriod for quite
	 * reliable, always asynchronous Connection testing.
	 */
	@Column(columnDefinition="TINYINT(1) DEFAULT FALSE")
	private Boolean testConnectionOnCheckin;  

	
	@ManyToOne
	@JoinColumn(name="groupId")
	private XmppServerGroup xmppServerGroup;
	
	@Transient
	private AtomicLong deviceCounter = new AtomicLong();
	
	@Column (name="properties", length=5048)
	private HashMap<String, String> properties;

	@Override
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	@Override
	public Integer getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String getDatabasename() {
		return databasename;
	}
	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}
	@Override
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isPasswordEncrypted() {
		return isPasswordEncrypted;
	}
	public void setPasswordEncrypted(boolean isPasswordEncrypted) {
		this.isPasswordEncrypted = isPasswordEncrypted;
	}
	@Override
	public Integer getMaxConnectionPerHost() {
		return maxConnectionPerHost;
	}
	public void setMaxConnectionPerHost(int maxConnectionPerHost) {
		this.maxConnectionPerHost = maxConnectionPerHost;
	}
	@Override
	public Integer getIdleConnectionPerHost() {
		return idleConnectionPerHost;
	}
	public void setIdleConnectionPerHost(int idleConnectionPerHost) {
		this.idleConnectionPerHost = idleConnectionPerHost;
	}
	@Override
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	@Override
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public Integer getLoadFactor() {
		return loadFactor;
	}
	public void setLoadFactor(Integer loadFactor) {
		this.loadFactor = loadFactor;
	}
	@Override
	public long getDeviceCounter() {
		return deviceCounter.longValue();
	}
	
	@Override
	public void incrementDeviceCounter()
	{
		deviceCounter.incrementAndGet();
	}
	@Override
	public void decrementDeviceCounter()
	{
		deviceCounter.decrementAndGet();
	}
	@Override
	public Boolean getIsPasswordEncrypted() {
		return isPasswordEncrypted;
	}
	@Override
	public Integer getShardId() {
		return shardId;
	}
	@Override
	public Integer getMaxIdleTime() {
		return maxIdleTime;
	}
	public void setMaxIdleTime(Integer maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}
	@Override
	public String getPreferredTestQuery() {
		return preferredTestQuery;
	}
	public void setPreferredTestQuery(String preferredTestQuery) {
		this.preferredTestQuery = preferredTestQuery;
	}
	@Override
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	@Override
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public void setMaxConnectionPerHost(Integer maxConnectionPerHost) {
		this.maxConnectionPerHost = maxConnectionPerHost;
	}
	@Override
	public Integer getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}
	public void setIdleConnectionTestPeriod(Integer idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}
	@Override
	public XmppServerGroup getXmppServerGroup() {
		return xmppServerGroup;
	}
	public void setXmppServerGroup(XmppServerGroup xmppServerGroup) {
		this.xmppServerGroup = xmppServerGroup;
	}
	
	
	public void setMaxIdleTimeExcessConnections(Integer maxIdleTimeExcessConnections) {
		this.maxIdleTimeExcessConnections = maxIdleTimeExcessConnections;
	}
	
	public void setCheckoutTimeout(Integer checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}
	
	@Override
	public Integer getMaxIdleTimeExcessConnections() {
		return this.maxIdleTimeExcessConnections;
	}
	

	@Override
	public Integer getCheckoutTimeout() {
		return this.checkoutTimeout;
	}
	
	public Integer getAcquireIncrement() {
		return acquireIncrement;
	}
	
	public void setAcquireIncrement(Integer acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}
	
	@Override
	public Integer getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}
	
	public void setAcquireRetryAttempts(Integer acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}
	
	@Override
	public Integer getAcquireRetryDelay() {
		return acquireRetryDelay;
	}
	
	public void setAcquireRetryDelay(Integer acquireRetryDelay) {
		this.acquireRetryDelay = acquireRetryDelay;
	}
	
	@Override
	public Integer getMaxStatements() {
		return maxStatements;
	}
	public void setMaxStatements(Integer maxStatements) {
		this.maxStatements = maxStatements;
	}
	
	@Override
	public Integer getNumHelperThreads() {
		return numHelperThreads;
	}
	
	public void setNumHelperThreads(Integer numHelperThreads) {
		this.numHelperThreads = numHelperThreads;
	}
	
	@Override
	public Boolean getTestConnectionOnCheckout() {
		return testConnectionOnCheckout;
	}
	
	public void setTestConnectionOnCheckout(Boolean testConnectionOnCheckout) {
		this.testConnectionOnCheckout = testConnectionOnCheckout;
	}
	
	@Override
	public Boolean getTestConnectionOnCheckin() {
		return testConnectionOnCheckin;
	}
	public void setTestConnectionOnCheckin(Boolean testConnectionOnCheckin) {
		this.testConnectionOnCheckin = testConnectionOnCheckin;
	}

	@Override
	public boolean isOpenForRegistration() {
		return getLoadFactor() == null || getLoadFactor() > 0;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShardImpl shard = (ShardImpl) o;

        if (shardId != shard.shardId) return false;
        if (!acquireIncrement.equals(shard.acquireIncrement)) return false;
        if (!acquireRetryAttempts.equals(shard.acquireRetryAttempts)) return false;
        if (!acquireRetryDelay.equals(shard.acquireRetryDelay)) return false;
        if (!checkoutTimeout.equals(shard.checkoutTimeout)) return false;
        if (code != null ? !code.equals(shard.code) : shard.code != null) return false;
        if (databasename != null ? !databasename.equals(shard.databasename) : shard.databasename != null) return false;
        if (driverClass != null ? !driverClass.equals(shard.driverClass) : shard.driverClass != null) return false;
        if (hostname != null ? !hostname.equals(shard.hostname) : shard.hostname != null) return false;
        if (idleConnectionPerHost != null ? !idleConnectionPerHost.equals(shard.idleConnectionPerHost) : shard.idleConnectionPerHost != null)
            return false;
        if (idleConnectionTestPeriod != null ? !idleConnectionTestPeriod.equals(shard.idleConnectionTestPeriod) : shard.idleConnectionTestPeriod != null)
            return false;
        if (isPasswordEncrypted != null ? !isPasswordEncrypted.equals(shard.isPasswordEncrypted) : shard.isPasswordEncrypted != null)
            return false;
        if (jdbcUrl != null ? !jdbcUrl.equals(shard.jdbcUrl) : shard.jdbcUrl != null) return false;
        if (loadFactor != null ? !loadFactor.equals(shard.loadFactor) : shard.loadFactor != null) return false;
        if (maxConnectionPerHost != null ? !maxConnectionPerHost.equals(shard.maxConnectionPerHost) : shard.maxConnectionPerHost != null)
            return false;
        if (maxIdleTime != null ? !maxIdleTime.equals(shard.maxIdleTime) : shard.maxIdleTime != null) return false;
        if (!maxIdleTimeExcessConnections.equals(shard.maxIdleTimeExcessConnections)) return false;
        if (!maxStatements.equals(shard.maxStatements)) return false;
        if (!numHelperThreads.equals(shard.numHelperThreads)) return false;
        if (password != null ? !password.equals(shard.password) : shard.password != null) return false;
        if (port != null ? !port.equals(shard.port) : shard.port != null) return false;
        if (preferredTestQuery != null ? !preferredTestQuery.equals(shard.preferredTestQuery) : shard.preferredTestQuery != null)
            return false;
        if (region != null ? !region.equals(shard.region) : shard.region != null) return false;
        if (!testConnectionOnCheckin.equals(shard.testConnectionOnCheckin)) return false;
        if (!testConnectionOnCheckout.equals(shard.testConnectionOnCheckout)) return false;
        if (type != null ? !type.equals(shard.type) : shard.type != null) return false;
        if (userName != null ? !userName.equals(shard.userName) : shard.userName != null) return false;
        if (xmppServerGroup != null && shard.getXmppServerGroup()!= null && (xmppServerGroup.getGroupId() != shard.xmppServerGroup.getGroupId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shardId;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (databasename != null ? databasename.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (isPasswordEncrypted != null ? isPasswordEncrypted.hashCode() : 0);
        result = 31 * result + (maxConnectionPerHost != null ? maxConnectionPerHost.hashCode() : 0);
        result = 31 * result + (idleConnectionPerHost != null ? idleConnectionPerHost.hashCode() : 0);
        result = 31 * result + (maxIdleTime != null ? maxIdleTime.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (loadFactor != null ? loadFactor.hashCode() : 0);
        result = 31 * result + (preferredTestQuery != null ? preferredTestQuery.hashCode() : 0);
        result = 31 * result + (idleConnectionTestPeriod != null ? idleConnectionTestPeriod.hashCode() : 0);
        result = 31 * result + (jdbcUrl != null ? jdbcUrl.hashCode() : 0);
        result = 31 * result + (driverClass != null ? driverClass.hashCode() : 0);
        result = 31 * result + maxIdleTimeExcessConnections.hashCode();
        result = 31 * result + checkoutTimeout.hashCode();
        result = 31 * result + acquireIncrement.hashCode();
        result = 31 * result + acquireRetryAttempts.hashCode();
        result = 31 * result + acquireRetryDelay.hashCode();
        result = 31 * result + maxStatements.hashCode();
        result = 31 * result + numHelperThreads.hashCode();
        result = 31 * result + testConnectionOnCheckout.hashCode();
        result = 31 * result + testConnectionOnCheckin.hashCode();
        result = 31 * result + (xmppServerGroup != null ? xmppServerGroup.hashCode() : 0);
        return result;
    }
}

package com.uttara.example.AwsMigrationApiGateway.utility;

public interface TsApiGatewayConstants {

    public static final String namespacePrefix = "/ns1";
    public static final String SHARD_CODE = "shardCode";
    public static final String HOST_NAME = "hostname";
    public static final String SHARD_CODE_HOST_NAME = "shardCodeWithHostname";
    public static final String DEVICE_EMAIL_ID = "deviceEmailId";
    public static final String PRINTER_CLOUD_ID = "printerID";
    public static final String OWNER_SHIP_ID = "ownershipID";
    public static final String JOB_ID = "jobId";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEVICE_ID_XPATH = "/ns1:PrintJob/ns1:PrintJobProcessingElements/ns1:DeviceId/text()";
    public static final String DEVICE_EMAILID_XPATH = "/ns1:PrintJob/ns1:PrintJobProcessingElements/ns1:DeviceEmailId/text()";
    public static final String HTTPS = "https://";
    public static final String AMAZON_AWS = "amazonaws";
    public static final String ONRAMP = "/onramp";
    public static final String EPRINT_CENTER = "/eprintcenter";
    public static final String OFFRAMP = "/offramp";
    public static final String PRINT_JOB_URI = "/jobs/printjobs/";
    public static final String DEVICE_JOB_URI = "/devices/printers/";
    public static final String RENDER_JOB_URI = "/jobs/renderjobs/";
    public static final String SCAN_JOB_URI = "/jobs/scanjobs/";
    public static final String DELIVERY_ONLY_JOB_URI = "/jobs/deliveryonlyjobs/";
    public static final String SESSION_TOKEN = "/tokens/session/token";
    public static final String SCERET_TOKEN = "/tokens/session/secret";
    public static final String OFFRAMP_PRINTERS = "/Printers/";
    public static final String OWNER_SHIP = "/ownerships";


}
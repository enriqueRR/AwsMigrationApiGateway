package com.uttara.example.AwsMigrationApiGateway.helper;

/**
 * It should contain all CloudPrint URIs and logic for construction
 */
public class EprintURIHelper {

    public static final String OPTIN_TYPE ="optinType" ;
    public static final String SCHEDULE ="schedule" ;
    private static final String UriSeperator = "/";

    // Restlet url attributes
    public static final String PRINTER_ID = "PrinterID";
    public static final String OWNERSHIP_ID = "OwnershipID";
    public static final String JOBID = "JobID";
    public static final String DOCUMENTID = "DocumentID";
    public static final String PAGE_QUERYSTRING = "page";
    public static final String RESULTS_PER_PAGE = "resultsperpage";
    public static final String WHITELIST_ID = "WhiteListID";
    public static final String EmailId = "emailid";
    public static final String OWNER_ID = "ownerid";
    public static final String GET_FAVORITES = "getFavorites";
    public static final String GET_MORE_PRINTER_DETAILS = "getMorePrinterDetails";

    // Uri Constants
    private static final String ParameterLeftDelimiter = "{";
    private static final String ParameterRightDelimiter = "}";
    private static final String OwnershipsUri = "ownerships";
    private static final String PrintJobStatusUri = "status";


    /**
     * @param parameterName
     * @return "{" + parameterName + "}"
     */
    public static String getParameterURI(String parameterName) {
        return ParameterLeftDelimiter + parameterName + ParameterRightDelimiter;
    }


    public static String getPrintJobURI(String jobId) {
        return getPrintJobsURI() + jobId + UriSeperator;
    }


    public static String getCancelURI(String jobId) {
        return getPrintJobURI(jobId) + "cancel/";
    }

    public static String getPrintJobsURI() {
        return "/jobs/printjobs/";
    }

    /**
     * Returns the Print job status uri. /jobs/printjobs/{jobId}/status
     *
     * @param jobId
     * @return
     */
    public static String getPrintJobStatusURI(String jobId) {
        return getPrintJobURI(jobId) + PrintJobStatusUri;
    }

    public static String getEmailAddressURIOnRamp(String printerId) {
        return getPrinterURIOnRamp(printerId) + "emailaddress/";
    }

    /**
     * @return "/ownerships"
     */
    public static String getOwnerShipsURI() {
        return "/" + OwnershipsUri;
    }

    /**
     * @return "/ownerships"
     */
    public static String getOwnerShipURI(String ownerShipId) {
        return getOwnerShipsURI() + UriSeperator + ownerShipId + UriSeperator;
    }

    public static String getPrintersURIOnRamp() {
        return "/devices/printers/";
    }

    public static String getPrinterURIOnRamp(String printerId) {
        return getPrintersURIOnRamp() + printerId + UriSeperator;

    }

    public static String getCloudConfigURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "cloudconfiguration/";
    }

    public static String getWhiteListURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "whitelist/";
    }

    public static String getWhiteListEntryURI(String printerId,
                                              String WhiteListID) {
        return getWhiteListURI(printerId) + WhiteListID + "/";
    }

    public static String getPrinterPrintJobsURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "printjobs/";
    }

    public static String getPrinterSupportedProcesssingElementsURI(
            String printerId) {
        return getPrinterURIOnRamp(printerId) + "supportedprocessingelements/";
    }

    public static String getPrinterStatusURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "status/";
    }

    public static String getPrinterDefaultProcessingElementsURI(
            String printerId) {
        // TODO Auto-generated method stub
        return getPrinterURIOnRamp(printerId) + "defaultprocessingelements/";
    }

    public static String getBlackListURI(String printerId, String blackListID) {
        return getPrinterURIOnRamp(printerId) + "blacklist" + UriSeperator + blackListID;
    }


    public static String getOwnerAdditionalEmailAddressLink(String ownerAdditionalEmailAddressID) {
        return "/users/AdditionalEmailAddress/" + ownerAdditionalEmailAddressID;
    }
}

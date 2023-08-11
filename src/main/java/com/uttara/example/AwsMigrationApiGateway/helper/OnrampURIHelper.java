package com.uttara.example.AwsMigrationApiGateway.helper;

     /**
        * This is  containing  all CloudPrint URIs and logic for construction
        */
public class OnrampURIHelper {


    private static final String UriSeperator = "/";

    // Restlet url attributes
    public static final String PRINTER_ID = "PrinterID";
    public static final String DEVICE_ID = "deviceid";
    public static final String DEVICE_EMAIL_ADDRESS = "deviceemailaddress";
    public static final String OWNERSHIP_ID = "OwnershipID";
    public static final String JOBID = "JOBID";
    public static final String DOCUMENTID = "DOCUMENTID";
    public static final String PAGE_QUERYSTRING = "Page";
    public static final String RESULTS_PER_PAGE = "ResultsPerPage";
    public static final String WHITELIST_ID = "WhiteListID";
    public static final String EmailId = "emailid";
    public static final String SESSION_TOKEN = "sessiontoken";
    // Uri Constants
    private static final String ParameterLeftDelimiter = "{";
    private static final String ParameterRightDelimiter = "}";
    private static final String OwnershipsUri = "ownerships";
    private static final String PrintJobStatusUri = "status";
    private static final String RenderJobStatusUri = "status";
    private static final String DeliveryOnlyJobStatusUri = "status";
    private static final String DocumentsUri = "documents" + UriSeperator;
    private static final String ProcessingElements = "processingelements" + UriSeperator;
    // Added for preview
    private static final String PreviewUri = "preview" + UriSeperator;
    private static final String PreviewJobStatusUri = "status" + UriSeperator;
    private static final String PreviewProcessingElementsUri = "processingelements" + UriSeperator;
    private static final String PreviewOutputUri = "output" + UriSeperator;
    private static final String eSCLScanJobStatusUri = "scannerstatus";



    /**
     * @param parameterName
     * @return "{" + parameterName + "}"
     */
    public static String getParameterURI(String parameterName) {
        return ParameterLeftDelimiter + parameterName + ParameterRightDelimiter;
    }

    public static String getPrintJobDocumentsURI(String jobId) {
        return getPrintJobURI(jobId) + DocumentsUri;
    }


    public static String getPrintJobURI(String jobId) {
        return getPrintJobsURI() + jobId + UriSeperator;
    }

    public static String getRenderJobURI(String jobId) {
        return getRenderJobsURI() + jobId + UriSeperator;
    }

    public static String getPrintJobProcessingElementsURI(String jobId) {
        return getPrintJobURI(jobId) + ProcessingElements;
    }

    public static String getPrintJobDocumentURI(String jobId, String documentID) {
        return getPrintJobDocumentsURI(jobId) + documentID + UriSeperator;
    }

    public static String getPrintJobDocumentDataURI(String jobId,
                                                    String documentID) {
        return getPrintJobDocumentURI(jobId, documentID) + "data/";
    }

    public static String getPrintJobDocumentOutputURI(String jobId,
                                                      String documentID) {
        return getPrintJobDocumentURI(jobId, documentID) + "output/";
    }

    public static String getPrintURI(String jobId) {
        return getPrintJobURI(jobId) + "print/";
    }

    public static String getCancelURI(String jobId) {
        return getPrintJobURI(jobId) + "cancel/";
    }

    public static String getRenderJobCancelURI(String jobId) {
        return getRenderJobURI(jobId) + "cancel/";
    }

    //Added for preview
    public static String getPreviewURI(String jobId, String documentID) {
        return getPrintJobDocumentURI(jobId, documentID) + PreviewUri ;
    }

    public static String getPreviewStatusURI(String jobId, String documentID) {
        return getPreviewURI(jobId, documentID) + PreviewJobStatusUri ;
    }

    public static String getPreviewProcessingElementsURI(String jobId, String documentId){
        return getPreviewURI(jobId, documentId) + PreviewProcessingElementsUri;
    }

    public static String getPreviewOutputURI(String jobId, String documentId){
        return getPreviewURI(jobId, documentId) + PreviewOutputUri;
    }

    public static String getPrintJobsURI() {
        return "/jobs/printjobs/";
    }


    /**
     * Returns the Print job status uri.
     * /jobs/printjobs/{jobId}/status
     * @param jobId
     * @return
     */
    public static String getPrintJobStatusURI(String jobId) {
        return getPrintJobURI(jobId) + PrintJobStatusUri;
    }

    /**
     * Returns the Render job status uri.
     * /jobs/renderjobs/{jobId}/status
     * @param jobId
     * @return
     */
    public static String getRenderJobStatusURI(String jobId) {
        return getRenderJobURI(jobId) + RenderJobStatusUri;
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

    public static String getOwnersURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "owners/";
    }

    public static String getCloudConfigURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "cloudconfiguration/";
    }

    public static String getWhiteListURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "whitelist/";
    }
    public static String getWhiteListEntryURI(String printerId,String WhiteListID) {
        return getWhiteListURI(printerId) + WhiteListID+"/";
    }
    public static String getPrinterPrintJobsURI(String printerId) {
        return getPrinterURIOnRamp(printerId) + "printjobs/";
    }

    public static String getPrinterSupportedProcesssingElementsURI(
            String printerId) {
        return getPrinterURIOnRamp(printerId) + "supportedprocessingelements/";
    }

    public static String getPrinterStatusURI(
            String printerId) {
        return getPrinterURIOnRamp(printerId) + "status/";
    }

    public static String getPrintJobDocumentProcessingElement(String jobId, String documentId){
        return getPrintJobDocumentURI(jobId, documentId) + ProcessingElements;
    }

    public static String getPrintJobDocumentStatus(String jobId, String documentId){
        return getPrintJobDocumentURI(jobId, documentId) + "status" + UriSeperator;
    }


    // added for the rendering jobs


    public static String getRenderJobsURI() {
        return "/jobs/renderjobs/";
    }

    public static String getRenderJobDocumentsURI(String jobId) {
        return getRenderJobURI(jobId) + DocumentsUri;
    }


    public static String getRenderJobProcessingElementsURI(String jobId) {
        return getRenderJobURI(jobId) + ProcessingElements;
    }

    public static String getRenderJobDocumentURI(String jobId, String documentID) {
        return getRenderJobDocumentsURI(jobId) + documentID + UriSeperator;
    }

    public static String getRenderJobDocumentDataURI(String jobId,
                                                     String documentID) {
        return getRenderJobDocumentURI(jobId, documentID) + "data/";
    }

    public static String getRenderJobDocumentOutputURI(String jobId,
                                                       String documentID) {
        return getRenderJobDocumentURI(jobId, documentID) + "output/";
    }

    public static String getRenderURI(String jobId) {
        return getRenderJobURI(jobId) + "render/";
    }

    public static String getRenderCancelURI(String jobId) {
        return getRenderJobURI(jobId) + "cancel/";
    }

    public static String getRenderJobDocumentProcessingElement(String jobId, String documentId){
        return getRenderJobDocumentURI(jobId, documentId) + ProcessingElements;
    }

    public static String getRenderJobDocumentStatus(String jobId, String documentId){
        return getRenderJobDocumentURI(jobId, documentId) + "status" + UriSeperator;
    }

    //Scan Job URIs
    public static String getScanJobDocumentOutputURI(String jobId,
                                                     String documentID) {
        return getScanJobDocumentURI(jobId, documentID) + "output/";
    }

    public static String getScanJobDocumentURI(String jobId, String documentID) {
        return getScanJobDocumentsURI(jobId) + documentID + UriSeperator;
    }

    public static String getScanJobDocumentsURI(String jobId) {
        return getScanJobURI(jobId) + DocumentsUri;
    }


    public static String getScanJobURI(String jobId) {
        return getScanJobsURI() + jobId + UriSeperator;
    }

    public static String getScanCancelURI(String jobId) {
        return getScanJobsURI()+jobId+ UriSeperator + "cancel" + UriSeperator;
    }

    public static String getScanJobsURI() {
        return "/jobs/scanjobs/";
    }

    public static String getDeliveryOnlyJobCancelURI(String jobId) {
        return getDeliveryOnlyJobURI(jobId) + "cancel/";
    }

    public static String getDeliveryOnlyJobURI(String jobId) {
        return getDeliveryOnlyJobsURI() + jobId + UriSeperator;
    }

    public static String getDeliveryOnlyJobsURI() {
        return "/jobs/deliveryonlyjobs/";
    }

    public static String getDeliveryOnlyJobDocumentURI(String jobId, String documentID) {
        return getDeliveryOnlyJobDocumentsURI(jobId) + documentID + UriSeperator;
    }

    public static String getDeliveryOnlyJobDocumentsURI(String jobId) {
        return getDeliveryOnlyJobURI(jobId) + DocumentsUri;
    }

    public static String getDeliveryOnlyJobDocumentStatus(String jobId, String documentId){
        return getDeliveryOnlyJobDocumentURI(jobId, documentId) + "status" + UriSeperator;
    }

    public static String getDeliveryOnlyJobStatusURI(String jobId) {
        return getDeliveryOnlyJobURI(jobId) + DeliveryOnlyJobStatusUri;
    }

    public static String getDeliveryOnlyJobDocumentDataURI(String jobId,
                                                           String documentID) {
        return getDeliveryOnlyJobDocumentURI(jobId, documentID) + "data/";
    }

    public static String getDeliveryOnlyJobDocumentOutputURI(String jobId,
                                                             String documentID) {
        return getDeliveryOnlyJobDocumentURI(jobId, documentID) + "output/";
    }

    public static String getDeliveryOnlyJobProcessingElementsURI(String jobId) {
        return getDeliveryOnlyJobURI(jobId) + ProcessingElements;
    }

    public static String getESCLScanDocURI(String scannerId, String jobId, String docId) {
        return getESCLScanJobURI(scannerId, jobId) + "/documents/" + docId;
    }

    public static String getESCLScanJobURI(String scannerId, String jobId) {
        return getESCLScanURI(scannerId) + "/ScanJobs/" + jobId;
    }

    public static String getESCLScanJobStatusURI(String printerId) {
        return getESCLScanURI(printerId) + UriSeperator +eSCLScanJobStatusUri;
    }

    private static String getESCLScanURI(String scannerId) {
        return "/printers/" + scannerId;
    }
}

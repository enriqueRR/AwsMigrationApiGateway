package com.uttara.example.AwsMigrationApiGateway.helper;

/**
 * It should contain all CloudPrint URIs and logic for construction
 */
public class offRampURIHelper {
    private static final String OFF_RAMP_LEFT_OVER_URL = "/";
    public static final String PRINTER_ID = "PrinterID";
    public static final String EMAIL_ADDRESS= "EmailAddress";
    public static final String OWNERSHIP_ID = "OwnershipID";
    private static final String OwernershipsUri = "/ownerships";
    public static final String JOBID = "JOBID";
    public static final String DOCUMENTID = "DOCUMENTID";
    public static final String PAGE = "PAGE";
    public static final String SESSION_TOKEN = "sessiontoken";
    public static final String HOST_NAME = "sessionserver";

    /**
     * @param parameterName
     * @return "{" + parameterName + "}"
     */
    public static String getParameterURI(String parameterName) {
        return "{" + parameterName + "}";
    }

//	public static String getPrintJobDocumentsURI(String jobId) {
//		return getPrintJobURI(jobId) + "documents/";
//	}

//	public static String getPrintJobURI(String jobId) {
//		return getPrintJobsURI() + jobId + "/";
//	}

//	public static String getPrintJobProcessingElementsURI(String jobId) {
//		return getPrintJobURI(jobId) + "processingelements/";
//	}

//	public static String getPrintJobDocumentURI(String jobId, String documentID) {
//		return getPrintJobDocumentsURI(jobId) + documentID + "/";
//	}

//	public static String getPrintJobDocumentDataURI(String jobId,
//			String documentID) {
//		return getPrintJobDocumentURI(jobId, documentID) + "data/";
//	}
//
//	public static String getPrintJobDocumentOutputURI(String jobId,
//			String documentID) {
//		return getPrintJobDocumentURI(jobId, documentID) + "output/";
//	}
//
//	public static String getPrintURI(String jobId) {
//		return getPrintJobURI(jobId) + "print/";
//	}
//
//	public static String getCancelURI(String jobId) {
//		return getPrintJobURI(jobId) + "cancel/";
//	}
//
//	public static String getPreviewURI(String jobId) {
//		return getPrintJobURI(jobId) + "preview/";
//	}

//	public static String getPrintJobsURI() {
//		return OffRampAPIConfig.getInstance().getStringValue("OnRampLeftOverURL")
//				+ "jobs/printjobs/";
//	}

    /**
     * @return /CloudPrint/Printers
     */
    public static String getPrintersURI() {
        // have to change /CloudPrint to /Devices hence keeping this in config
        return OFF_RAMP_LEFT_OVER_URL + "Printers";
    }

    public static String getPrinterURI(String printerId) {
        // have to change /CloudPrint to /Devices hence keeping this in config
        return getPrintersURI()  + "/" + printerId + "/";
    }

    /**
     * {@code String OpaquePrinterURL = "OpaquePrinterURL";
     * mainRoute.attach(URIHelper.getEmailAddressURI(" " + OpaquePrinterURL + "}
     * ") , EmailAddressResource.class); }
     *
     * @param opaqueURI
     * @return /CloudPrint/Printers/opaqueURI/emailaddress
     */
    public static String getEmailAddressURI(String opaqueURI) {
        return getPrinterURI(opaqueURI) + "EmailAddress";
    }

//	public static String getEmailAddressURIOnRamp(String printerId) {
//		return getPrinterURIOnRamp(printerId) + "emailaddress/";
//	}

    /**
     * @return "/ownerships"
     */
    public static String getOwnerShipsURI() {
        return OwernershipsUri;
    }

    /**
     * @return "/ownerships"
     */
    public static String getOwnerShipURI(String ownerShipId) {
        return OwernershipsUri + "/" + ownerShipId + "/";
    }

//	public static String getPrintersURIOnRamp() {
//		return OffRampAPIConfig.getInstance().getStringValue("OnRampLeftOverURL") + "devices/printers/";
//	}

//	public static String getPrinterURIOnRamp(String printerId) {
//		return getPrintersURIOnRamp() + printerId + "/";
//
//	}

//	public static String getOwnersURI(String printerId) {
//		return getPrinterURIOnRamp(printerId) + "owners/";
//	}
//
//	public static String getCloudConfigURI(String printerId) {
//		return getPrinterURIOnRamp(printerId) + "cloudconfiguration/";
//	}
//
//	public static String getWhiteListURI(String printerId) {
//		return getPrinterURIOnRamp(printerId) + "whitelist/";
//	}

    /**
     * @param parameter
     *            it will be fitted in the URI. for eg. {PrinterID} in
     *            /Printer/{PrinterID}
     * @return the URI that is to be used for instruction resource. Doesnt
     *         includes "/" at the end.
     */
    public static String getInstructionURI(String parameter) {
        return getPrintersURI() + "/" + parameter + "/PrintJobs/Instructions";
    }

    public static String getXMPPConfigURI(String parameter) {
        // OffRamp/Printers/{}/XMPPConfiguration
        return getPrintersURI() + "/"  + parameter + "/XMPPConfiguration";
    }

    public static String getPrinterCloudConfigURI(String parameter) {
        return getPrintersURI() + "/"  + parameter + "/CloudConfiguration";
    }

    public static String getPrinterStateReportURI(String parameter) {
        return getPrintersURI() + "/"  + parameter + "/StateReport";
    }

//	public static String getPrinterSupportedProcesssingElementsURI(
//			String printerId) {
//		return getPrinterURIOnRamp(printerId) + "supportedProcessingElements/";
//	}

    public static String getOutputURI(String jobID, String documentID)
    {
        return getPrintersURI() + "/" + "PrintJobs/Output/" + getParameterURI(jobID) + "/" + getParameterURI(documentID);
    }

//	public static String setPrintJobsUnderPrinterURI(String printerId) {
//		return getPrinterURIOnRamp(printerId) + "printjobs/";
//	}
}

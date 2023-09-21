package com.uttara.example.AwsMigrationApiGateway.utility;

import java.io.StringReader;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import static com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants.namespacePrefix;


public interface Parsing {

    public static String getDeviceEmailId(String body, String Xpath) {
        String deviceEmaild = new String();
        try {
            //Parse XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(body)));

            //Get XPath expression
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            xpath.setNamespaceContext(new NamespaceResolver(doc));
            XPathExpression expr = xpath.compile(Xpath);
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            deviceEmaild = nodes.item(0) != null ? nodes.item(0).getNodeValue() : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceEmaild;
    }

    public static String getJobId(String str) {
        String[] arr = str.split("/");
        return arr[0];
    }
    public static String getPrinterEmailId(String body) {
        //Parse XML file
        String printerEmailId =null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(body)));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("EmailAddress");
            for (int i = 0; i < list.getLength(); i++) {
                //Getting one node from the list.
                Node childNode = list.item(i);
                printerEmailId = childNode.getTextContent();
                        }
       } catch (Exception e) {
            e.printStackTrace();
        }
        return printerEmailId;
    }

    public static String getDeviceEmail(String body) {
        //Parse XML file
        String printerEmailId =null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(body)));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("DeviceEmailId");
            for (int i = 0; i < list.getLength(); i++) {
                //Getting one node from the list.
                Node childNode = list.item(i);
                printerEmailId = childNode.getTextContent();}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printerEmailId;
    }
    public static String getDeviceEmailID(String body) {
        //Parse XML file
        String printerEmailId =null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(body)));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("DeviceEmailID");
            for (int i = 0; i < list.getLength(); i++) {
                //Getting one node from the list.
                Node childNode = list.item(i);
                printerEmailId = childNode.getTextContent();}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printerEmailId;
    }
    public static String getDeviceId(String body) {
        //Parse XML file
        String deviceId =null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(body)));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("DeviceId");
            for (int i = 0; i < list.getLength(); i++) {
                //Getting one node from the list.
                Node childNode = list.item(i);
                deviceId = childNode.getTextContent();}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }
    public static String getDeviceID(String body) {
        //Parse XML file
        String deviceId =null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(body)));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("DeviceID");
            for (int i = 0; i < list.getLength(); i++) {
                //Getting one node from the list.
                Node childNode = list.item(i);
                deviceId = childNode.getTextContent();}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }
}

class NamespaceResolver implements NamespaceContext {
    //Store the source document to search the namespaces
    private Document sourceDocument;

    public NamespaceResolver(Document document) {
        sourceDocument = document;
    }

    //The lookup for the namespace uris is delegated to the stored document.
    public String getNamespaceURI(String prefix) {
        if (prefix.equals(namespacePrefix)) {
            return sourceDocument.lookupNamespaceURI(null);
        } else {
            return sourceDocument.lookupNamespaceURI(prefix);
        }
    }

    public String getPrefix(String namespaceURI) {
        return sourceDocument.lookupPrefix(namespaceURI);
    }

    @SuppressWarnings("rawtypes")
    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
}
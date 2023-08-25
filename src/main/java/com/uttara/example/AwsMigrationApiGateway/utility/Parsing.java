package com.uttara.example.AwsMigrationApiGateway.utility;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public interface Parsing {
    public static final String namespacePrefix = "/ns1";
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
            deviceEmaild = nodes.item(0)!=null?nodes.item(0).getNodeValue():null;
        } catch (Exception e) {
            e.printStackTrace();
        }
          return deviceEmaild;
    }
    public static List<String> getJobId(List<String> str) {
        Pattern p = Pattern.compile("[^a-z0-9 ]");
        ArrayList<String> al = new ArrayList<>();
        for (int i = 0; i < str.size(); i++) {
            String s =str.get(i);
            s = s.substring(s.indexOf(0),s.indexOf(s.length()-2));
            Matcher m = p.matcher(s);
            boolean res = m.find();
            if (res) {
                String printerId = s;
                System.out.println(printerId);
                al.add(printerId);
            }
        }
        return al;
    }
    public static String getJobId(String str){
        String[] arr = str.split("/");
          return arr[0];
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
        if (prefix.equals("/ns1")) {
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

    public static String getPrinterID(String body, String Xpath) {
        String printerId = new String();
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
            printerId = nodes.item(0)!=null?nodes.item(0).getNodeValue():null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printerId;
    }

}
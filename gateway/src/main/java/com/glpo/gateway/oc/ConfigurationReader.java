package com.glpo.gateway.oc;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.springframework.stereotype.Component;

import com.glpo.gateway.mvc.exception.GatewayException;

@Component
public class ConfigurationReader {

    private static final String SERVICE_FORM_XML_TAG = "serviceForm";
    private static final String ADDITIONAL_FORM_XML_TAG = "additionalForm";

    public Element readAsrXML(File asrXml) {
	Document configXml = null;
	try {
	    // URL resourceURL =
	    // this.getClass().getClassLoader().getResource("/xml/ASR.xsd");
	    // this.getClass().getClassLoader().getResourceAsStream("/xml/ASR.xsd");
	    SAXBuilder builder = new SAXBuilder(new XMLReaderXSDFactory(new File(
		    "D:\\apache-tomcat-8.0.5\\webapps\\gateway\\WEB-INF\\classes\\xml\\ASR.xsd")));
	    configXml = builder.build(asrXml);
	} catch (IOException e) {
	    throw new GatewayException("Can't read ASR XML", e);
	} catch (JDOMException e) {
	    throw new GatewayException("ASR XML doesn't correspond to XML schema", e);
	}
	return configXml.getRootElement();
    }

    public Map<String, Map<String, String>> getAsrFormFromXML(Element configuration) {
	Map<String, Map<String, String>> formAttrNameToValueBindings = new HashMap<String, Map<String, String>>();
	List<Element> serviceForms = configuration.getChildren(SERVICE_FORM_XML_TAG);	
	List<Element> additionalForms = configuration.getChildren(ADDITIONAL_FORM_XML_TAG);
	
	RequestValidator.checkDuplicatesInServiceForms(serviceForms);

	System.out.println("Got " + serviceForms.size() + " primary forms");
	System.out.println("Got " + additionalForms.size() + " secondary forms");

	List<Element> allForms = new ArrayList<Element>(serviceForms.size() + additionalForms.size());
	allForms.addAll(serviceForms);
	allForms.addAll(additionalForms);
	
	RequestValidator.checkDuplicatesAttributes(allForms);

	for (Element form : allForms) {
	    String type = form.getAttributeValue("type").toUpperCase();
	    System.out.println("Processing form: " + type);

	    List<Element> formAttrs = form.getChildren("attr");
	    Map<String, String> attrToValue = new HashMap<String, String>(formAttrs.size());

	    for (Element attr : formAttrs) {
		String name = attr.getAttributeValue("name");
		String value = attr.getAttributeValue("value");
		System.out.println("Got attr: " + name + " value: " + value);
		attrToValue.put(name, value);
	    }
	    formAttrNameToValueBindings.put(type, attrToValue);
	}
	RequestValidator.checkAsrRequestStructure(formAttrNameToValueBindings);
	
	return formAttrNameToValueBindings;
    }

   
}

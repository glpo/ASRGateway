package com.glpo.gateway.oc;

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
public class ValidationConfiguration {
    private Map<String, Map<String, ParameterValidator>> formToAttrNameParametersValidators;

    private static final String VALIDATION_XSD = "/xml/ASRValidationConfiguration.xsd";
    private static final String VALIDATION_XML = "/xml/ASRValidationConfiguration.xml";
    // private static final String VALIDATION_XML = "/xml/ASRVC.xml";

    public static final String FORM_TAG = "form";
    public static final String VALIDATOR_TAG = "validator";
    public static final String NAME_TAG_ATTR = "name";
    public static final String CLASS_TAG_ATTR = "class";
    public static final String TARGET_ATTRIBUTES_TAG = "target_attributes";
    public static final String ADDITIONAL_PARAMS_TAG = "additional_params";
    public static final String ID_TAG_ATTR = "id";
    public static final String ERROR_MESSAGE_TAG_ATTR = "error_message";
    public static final String OTHERWISE_TAG = "otherwise";
    public static final String VALUE_TAG = "value";
    public static final String OPERATOR_TAG_ATTR = "operator";
    public static final String ATTR_TAG = "attr";

    public void loadConfiguration() {
	formToAttrNameParametersValidators = new HashMap<String, Map<String, ParameterValidator>>();

	Document configurationXml = null;
	try {
	    SAXBuilder builder = new SAXBuilder(new XMLReaderXSDFactory(this.getClass().getClassLoader()
		    .getResource(VALIDATION_XSD)));
	    configurationXml = builder.build(this.getClass().getClassLoader().getResourceAsStream(VALIDATION_XML));
	} catch (IOException e) {
	    throw new GatewayException("Can't read configuration XML", e);
	} catch (JDOMException e) {
	    throw new GatewayException("Configuration XML doesn't correspond to XML schema", e);
	}
	Element configuration = configurationXml.getRootElement();
	List<Element> forms = configuration.getChildren(FORM_TAG);
	for (Element form : forms) {
	    String formName = form.getAttributeValue(NAME_TAG_ATTR);
	    Map<String, ParameterValidator> attrNameParamaterValidators = new HashMap<String, ParameterValidator>();
	    List<Element> validators = form.getChildren(VALIDATOR_TAG);
	    for (Element validator : validators) {
		String className = validator.getAttributeValue(CLASS_TAG_ATTR);
		Class<ParameterValidator> c = null;
		try {
		    c = (Class<ParameterValidator>) Class.forName(className);
		    ParameterValidator parameterValidator = c.newInstance();
		    parameterValidator.loadConfiguration(validator.getChildren(), formName);

		    Element targetAttributes = validator.getChild(TARGET_ATTRIBUTES_TAG);
		    List<Element> ids = targetAttributes.getChildren(ID_TAG_ATTR);

		    for (Element id : ids) {
			attrNameParamaterValidators.put(id.getValue(), parameterValidator);
		    }
		} catch (ClassNotFoundException e) {
		    throw new GatewayException("Validator class: " + className
			    + ", specified in configuration XML, was not found", e);
		} catch (InstantiationException e) {
		    throw new GatewayException("Validator class: " + className
			    + ", specified in configuration XML, can't be instantiated", e);
		} catch (IllegalAccessException e) {
		    throw new GatewayException("Validator class: " + className
			    + ", specified in configuration XML, was not found", e);
		}
	    }
	    formToAttrNameParametersValidators.put(formName, attrNameParamaterValidators);
	}
    }

    public Map<String, Map<String, ParameterValidator>> getFormToAttrNameToParametersValidatorsMap() {
	return formToAttrNameParametersValidators;
    }

    public List<String> getValidatorsNamesWithAttributes() {
	List<String> result = new ArrayList<String>();

	Document configurationXml = null;
	try {
	    SAXBuilder builder = new SAXBuilder(new XMLReaderXSDFactory(this.getClass().getClassLoader()
		    .getResource(VALIDATION_XSD)));
	    configurationXml = builder.build(this.getClass().getClassLoader().getResourceAsStream(VALIDATION_XML));
	} catch (IOException e) {
	    throw new GatewayException("Can't read configuration XML", e);
	} catch (JDOMException e) {
	    throw new GatewayException("Configuration XML doesn't correspond to XML schema", e);
	}
	Element configuration = configurationXml.getRootElement();
	List<Element> forms = configuration.getChildren(FORM_TAG);
	int i = 0;
	for (Element form : forms) {
	    // String formName = form.getAttributeValue(NAME_TAG_ATTR);
	    List<Element> validators = form.getChildren(VALIDATOR_TAG);
	    for (Element validator : validators) {
		String className = validator.getAttributeValue(CLASS_TAG_ATTR);
		String validatorName = validator.getAttributeValue("name");
		Element targetAttributes = validator.getChild(TARGET_ATTRIBUTES_TAG);
		List<Element> ids = targetAttributes.getChildren(ID_TAG_ATTR);

		StringBuilder targetAttrs = new StringBuilder();
		for (Element id : ids) {
		    targetAttrs.append(id.getValue());
		    targetAttrs.append(", ");
		}
		String targetAttrsString = targetAttrs.toString().substring(0, targetAttrs.toString().length() -2);
		result.add("<tr> <td>" + (i + 1) + "</td> <td>" + validatorName + "</td> <td>" + targetAttrsString
			+ "</td> <td>" + className + "</td></tr>");
		i++;
	    }
	}

	return result;
    }

}

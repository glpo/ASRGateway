package com.glpo.gateway.oc;

import static com.glpo.gateway.oc.ValidationResultUtils.p;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.basic.BasicScrollPaneUI.ViewportChangeHandler;

import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glpo.gateway.mvc.exception.ASRStructureException;

@Component
public class RequestValidator {

    @Autowired
    ValidationConfiguration validationConfiguration;

    public List<String> validte(Map<String, Map<String, String>> asrFormToAttrNameValue) {
	validationConfiguration.loadConfiguration();
	Map<String, Map<String, ParameterValidator>> formToAttrNameParametersValidators = validationConfiguration
		.getFormToAttrNameToParametersValidatorsMap();
	List<String> errorMessages = new ArrayList<String>();

	ValidationHelper helper = new ValidationHelper();
	helper.setAttrNameToValueBinding(asrFormToAttrNameValue);
	helper.setFormToAttrNameParametersValidators(formToAttrNameParametersValidators);

	List<String> forms = helper.getAllForms();

	for (String form : forms) {
	    System.out.println("[VC] Getting attributes for form " + form);
	    List<String> attributes = helper.getAllAttributesForForm(form);
	    System.out.println("[VC] Got " + attributes != null ? attributes.size() : attributes + " attributes.");
	    for (String attr : attributes) {
		if (attr != null) {
		    ParameterValidator parameterValidator = helper.getParameterValidatorForAttrOnForm(form, attr);
		    if (parameterValidator != null) {
			String validationResult = parameterValidator.validate(form, attr, helper);
			if (validationResult != null && validationResult.length() > 0) {
			    validationResult = validationResult.replace("${attr}", attr);
			    validationResult = validationResult.replace("${form}", form);

			    errorMessages.add(validationResult);
			}
		    }
		}
	    }
	}
	return errorMessages;

    }

    private static Set<String> checkDuplicates(List<Element> elements, String attribute) {
	Set<String> duplicates = new HashSet<String>();
	Set<String> tempSet = new HashSet<String>();

	for (Element element : elements) {
	    String attr = element.getAttributeValue(attribute);
	    if (!tempSet.add(attr)) {
		duplicates.add(attr);
	    }
	}
	return duplicates;
    }

    public static void checkDuplicatesInServiceForms(List<Element> serviceForms) {
	Set<String> duplicateForms = checkDuplicates(serviceForms, "type");

	if (duplicateForms != null && duplicateForms.size() > 0) {
	    throw new ASRStructureException("Error in ASR Request structure. "
		    + "</br> There are duplicates in service specific forms: "
		    + Arrays.toString(duplicateForms.toArray()) + " </br> Please, check your XML");
	}
    }

    public static void checkDuplicatesAttributes(List<Element> allForms) {
	StringBuilder errorMessages = new StringBuilder();
	for (Element form : allForms) {
	    List<Element> formAttrs = form.getChildren("attr");

	    Set<String> duplicateAttrs = checkDuplicates(formAttrs, "name");
	    if (duplicateAttrs != null && duplicateAttrs.size() > 0) {
		errorMessages.append("There are duplicate attributes: " + Arrays.toString(duplicateAttrs.toArray())
			+ " for " + form.getAttributeValue("type") + " Form. <br/>");
	    }
	}

	if (errorMessages.length() > 0) {
	    throw new ASRStructureException(errorMessages.toString());
	}
    }

    public static void checkAsrRequestStructure(Map<String, Map<String, String>> asrRequest) {
	String reqtyp = getReqtypValue(asrRequest);

	boolean isVCATExists = isFormExists(asrRequest, "VCAT");
	boolean isTQExists = isFormExists(asrRequest, "TQ");
	boolean isEODExists = isFormExists(asrRequest, "EOD");
	boolean isVCExists = isFormExists(asrRequest, "VC");
	boolean isSALIExists = isFormExists(asrRequest, "SALI");
	boolean isMSLExists = isFormExists(asrRequest, "MSL");
	boolean isACIExists = isFormExists(asrRequest, "ACI");
	boolean isNAIExists = isFormExists(asrRequest, "NAI");

	if ("A".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "FGA")) {
		throw new ASRStructureException(p("For REQTYP1=A FGA Form should exists. Please, check Your request."));
	    }
	    if (isVCATExists || isTQExists || isEODExists || isVCExists) {
		throw new ASRStructureException(
			p("For REQTYP1=A VCAT, TQ, EOD, VC Forms should NOT exists. Please, check Your request."));
	    }
	} else if ("E".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "EUSA")) {
		throw new ASRStructureException(p("For REQTYP1=E EUSA Form should exists. Please, check Your request."));
	    }
	    if (isTQExists || isEODExists || isVCExists) {
		throw new ASRStructureException(
			p("For REQTYP1=E TQ, EOD, VC Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("L".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "TRUNKING")) {
		throw new ASRStructureException(
			p("For REQTYP1=L Trunking Form should exists. Please, check Your request."));
	    }
	    if (isSALIExists || isMSLExists || isVCATExists || isEODExists || isVCExists) {
		throw new ASRStructureException(
			p("For REQTYP1=L SALI, MSL, VCAT, EOD, VC Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("M".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "TRUNKING")) {
		throw new ASRStructureException(
			p("For REQTYP1=M Trunking Form should exists. Please, check Your request."));
	    }
	    if (isSALIExists || isMSLExists || isVCExists || isVCATExists) {
		throw new ASRStructureException(
			p("For REQTYP1=M SALI, MSL, VC, VCAT Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("R".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "Ring")) {
		throw new ASRStructureException(p("For REQTYP1=R Ring Form should exists. Please, check Your request."));
	    }
	    if (isTQExists || isVCExists || isEODExists || isMSLExists) {
		throw new ASRStructureException(
			p("For REQTYP1=R TQ, VC, EOD, MSL Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("S".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "TRANSPORT")) {
		throw new ASRStructureException(
			p("For REQTYP1=S Transport Form should exists. Please, check Your request."));
	    }
	    if (isACIExists || isTQExists || isEODExists || isVCExists) {
		throw new ASRStructureException(
			p("For REQTYP1=S ACI, TQ, EOD, VC Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("V".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "TRANSPORT")) {
		throw new ASRStructureException(
			p("For REQTYP1=V Transport Form should exists. Please, check Your request."));
	    }
	    if (isEODExists || isTQExists) {
		throw new ASRStructureException(
			p("For REQTYP1=V EOD, TQ Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("W".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "WAL")) {
		throw new ASRStructureException(p("For REQTYP1=W WAL Form should exists. Please, check Your request."));
	    }
	    if (isNAIExists || isEODExists || isTQExists || isVCATExists) {
		throw new ASRStructureException(
			p("For REQTYP1=W NAI, EOD, TQ, VCAT Forms should NOT exists. Please, check Your request."));
	    }

	} else if ("X".equals(reqtyp)) {
	    if (!isFormExists(asrRequest, "EUSA")) {
		throw new ASRStructureException(p("For REQTYP1=X EUSA Form should exists. Please, check Your request."));
	    }
	    boolean isMultiEcExists = isFormExists(asrRequest, "Multi EC");

	    if (isEODExists || isVCExists || isVCATExists || isMultiEcExists) {
		throw new ASRStructureException(
			p("For REQTYP1=X EOD, VC, VCAT, Multi EC Forms should NOT exists. Please, check Your request."));
	    }

	}
    }

    private static String getReqtypValue(Map<String, Map<String, String>> asrRequest) {
	Map<String, String> asrForm = asrRequest.get("ASR");
	if (asrForm == null || asrForm.isEmpty()) {
	    throw new ASRStructureException(p("Corresponding ASR XML have no ASR Form. </br> Please, check Your request."));
	}
	String reqtyp = asrForm.get("REQTYP1");
	if (reqtyp == null || reqtyp.isEmpty()) {
	    throw new ASRStructureException(
		    p("REQTYP1 filed have not filled on ASR Form but required.</br> Please, check Your request."));
	}
	return reqtyp;
    }

    private static boolean isFormExists(Map<String, Map<String, String>> asrRequest, String form) {
	Map<String, String> asrForm = asrRequest.get(form);
	if (asrForm == null /* || asrForm.isEmpty() */) {
	    return false;
	}
	return true;
    }
}

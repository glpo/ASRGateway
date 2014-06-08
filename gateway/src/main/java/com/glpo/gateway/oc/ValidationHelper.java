package com.glpo.gateway.oc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationHelper {
    private Map<String, Map<String, String>> formAttrNameToValueBindings;
    private Map<String, Map<String, ParameterValidator>> formToAttrNameParameterValidators;

    public void setAttrNameToValueBinding(Map<String, Map<String, String>> formAttrNameToValueBindings) {
	this.formAttrNameToValueBindings = formAttrNameToValueBindings;
    }

    public void setFormToAttrNameParametersValidators(
	    Map<String, Map<String, ParameterValidator>> formAttrNameParameterValidators) {
	this.formToAttrNameParameterValidators = formAttrNameParameterValidators;
    }

    public String getAttrValueForForm(String asrForm, String attrName) {
	Map<String, String> attrToValueMap = formAttrNameToValueBindings.get(asrForm);
	if (attrToValueMap != null && !attrToValueMap.isEmpty()) {
	    String value = attrToValueMap.get(attrName);
	    return value;
	}
	return null;
    }

    public List<String> getAllForms() {
	return new ArrayList<String>(formAttrNameToValueBindings.keySet());
    }
    
    public List<String> getAllAttributesForForm(String form) {
	Map<String, String> attrToValue = formAttrNameToValueBindings.get(form);
	if(attrToValue != null && !attrToValue.isEmpty()) {
	    return new ArrayList<String>(attrToValue.keySet());
	}
	return null;
    }

    public Map<String, String> getAttrNameValuesForForm(String form) {
	return formAttrNameToValueBindings.get(form);
    }
    
    public ParameterValidator getParameterValidatorForAttrOnForm(String form, String attr) {
	Map <String, ParameterValidator> attrNameToParameterValidator = formToAttrNameParameterValidators.get(form);
	if(attrNameToParameterValidator != null && !attrNameToParameterValidator.isEmpty()) {
	    return attrNameToParameterValidator.get(attr);
	}
	return null;
    }

}

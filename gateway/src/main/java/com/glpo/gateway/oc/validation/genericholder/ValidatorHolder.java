package com.glpo.gateway.oc.validation.genericholder;

public class ValidatorHolder {
    private String formType;   
    private String validatorName;
    private String validatorClass;
    private String targetAttributes;
    private String required;
    private String optional;
    private String prohibited;

    public ValidatorHolder() {

    }

    public ValidatorHolder(String formType, String validatorName, String validatorClass, String targetAttributes, String required,
	    String optional, String prohibited) {
	super();
	this.formType = formType;
	this.validatorName = validatorName;
	this.validatorClass = validatorClass;
	this.targetAttributes = targetAttributes;
	this.required = required;
	this.optional = optional;
	this.prohibited = prohibited;
    }
    
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }


    public String getValidatorName() {
	return validatorName;
    }

    public void setValidatorName(String validatorName) {
	this.validatorName = validatorName;
    }

    public String getValidatorClass() {
	return validatorClass;
    }

    public void setValidatorClass(String validatorClass) {
	this.validatorClass = validatorClass;
    }

    public String getTargetAttributes() {
	return targetAttributes;
    }

    public void setTargetAttributes(String targetAttributes) {
	this.targetAttributes = targetAttributes;
    }

    public String getRequired() {
	return required;
    }

    public void setRequired(String required) {
	this.required = required;
    }

    public String getOptional() {
	return optional;
    }

    public void setOptional(String optional) {
	this.optional = optional;
    }

    public String getProhibited() {
	return prohibited;
    }

    public void setProhibited(String prohibited) {
	this.prohibited = prohibited;
    }

}

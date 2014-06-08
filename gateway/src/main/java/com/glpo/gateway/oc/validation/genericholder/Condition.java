package com.glpo.gateway.oc.validation.genericholder;


import java.util.Collection;

public class Condition {
    private Operator operator;    
    private String attrName;
    private String asrForm;
    private Integer startPosition;
    private Integer endPosition;
    private Collection<String> values;    
    private boolean isAllKind;
    private boolean isOneKind;

    public Condition(Operator operator, String attrName) {
        this.operator = operator;
        this.attrName = attrName;
        if (operator.areValuesAcceptable) {
            throw new IllegalArgumentException("Values for selected operator are required");
        }
    }

    public Condition(Operator operator, String attrName, String asrForm, Integer startPosition, Integer endPosition,
             Collection<String> values) {
        this.operator = operator;
        this.attrName = attrName;
        this.asrForm = asrForm;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
       
        if (!operator.areValuesAcceptable() && (values != null && !values.isEmpty())) {
            throw new IllegalArgumentException("Values for selected operator are prohibited");
        }
        if (operator.areValuesAcceptable() && operator.isOnlyOneAcceptable() && values.size() > 1) {
            throw new IllegalArgumentException("Only one value is acceptable for operator \"" + operator.value() + "\"");
        }
        this.values = values;
    }

    public Condition(Operator operator, String attrName, String asrForm, Integer startPosition, Integer endPosition,
            boolean isAllKind, boolean isOneKind, Collection<String> values) {
        this(operator, attrName, asrForm, startPosition, endPosition, values);
        this.isAllKind = isAllKind;
        this.isOneKind = isOneKind;
    }
    public Operator getOperator() {
        return operator;
    }

    public Collection<String> getValues() {
        return values;
    }

    public String getAttrName() {
        return attrName;
    }
    
    public String getAsrForm() {
	return asrForm;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public boolean isAllKind() {
        return isAllKind;
    }

    public boolean isOneKind() {
        return isOneKind;
    }

}

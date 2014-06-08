package com.glpo.gateway.oc.validation.genericholder;

public enum Operator {
    //@formatter:off
    IN("in", true, false), // Value in list of constants
    NOT_IN("not in", true, false), // Value not in list of constants
    
    EMPTY("empty", false), // Value is empty
    NOT_EMPTY("not empty", false), // Value is not empty (filled with smth)
    
    EQ("eq", true, true), // Mathematical equals "="
    LT("lt", true, true), // Mathematical less than "<"
    GT("gt", true, true), // Mathematical greater than ">"
    LE("le", true, true), // Mathematical less than or equal "<="
    GE("ge", true, true), // Mathematical greater than or equal ">="
    
    EQUALS("equals", true, true),
    NOT_EQUALS("not equals", true, true);
    //@formatter:on

    private String text;
    boolean areValuesAcceptable;
    boolean isOnlyOneAcceptable;

    private Operator(String text, boolean areValuesAcceptable) {
        this.text = text;
        this.areValuesAcceptable = areValuesAcceptable;
    }

    private Operator(String text, boolean areValuesAcceptable, boolean isOnlyOneAcceptable) {
        this(text, areValuesAcceptable);
        this.isOnlyOneAcceptable = isOnlyOneAcceptable;
    }

    public String value() {
        return text;
    }

    public boolean areValuesAcceptable() {
        return areValuesAcceptable;
    }

    public boolean isOnlyOneAcceptable() {
        return isOnlyOneAcceptable;
    }

    public static Operator getOperator(String operation) {
        for (Operator operator : Operator.values()) {
            if (operator.value().equalsIgnoreCase(operation)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("Specified operation \"" + operation + "\" does not exist in configuration");
    }
}

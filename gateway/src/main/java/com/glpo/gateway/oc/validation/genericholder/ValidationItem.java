package com.glpo.gateway.oc.validation.genericholder;

import static com.glpo.gateway.oc.validation.genericholder.ItemKind.OPTIONAL;
import static com.glpo.gateway.oc.validation.genericholder.ItemKind.PROHIBITED;
import static com.glpo.gateway.oc.validation.genericholder.ItemKind.REQUIRED;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationItem {
    private Collection<Condition> conditions;
    private boolean isOtherwise;
    private ItemKind kind;
    private String errorMessage;
    private List<String> acceptableValues;

    public ValidationItem(ItemKind kind, Collection<Condition> conditions, boolean isOtherwise) {
        this.kind = kind;
        this.conditions = conditions;
        this.isOtherwise = isOtherwise;
        if (kind != REQUIRED && kind != OPTIONAL && kind != PROHIBITED) {
            throw new IllegalArgumentException(
                    "Parameter 'kind' can't be other than 'REQUIRED', 'OPTIONAL' or 'PROHIBITED'");
        }
    }

    public ValidationItem(ItemKind kind, Collection<Condition> conditions, boolean isOtherwise, String errorMessage) {
        this(kind, conditions, isOtherwise);
        if (kind == OPTIONAL && (errorMessage != null && !errorMessage.isEmpty())) {
            throw new IllegalArgumentException(
                    "Parameter 'errorMessage' can't be specified for Optional ValidateAttributeContents");
        }
        this.errorMessage = errorMessage;
    }

    public ValidationItem(ItemKind kind, Collection<Condition> conditions, boolean isOtherwise, String errorMessage,
            List<String> acceptableValues) {
        this(kind, conditions, isOtherwise, errorMessage);
        this.acceptableValues = acceptableValues;
    }

    public Collection<Condition> getAttrValidators() {
        return conditions;
    }

    public List<String> getAcceptableValues() {
        if (acceptableValues == null) {
            return Collections.EMPTY_LIST;
        } else {
            return acceptableValues;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isRequired() {
        return kind == REQUIRED;
    }

    public boolean isOptional() {
        return kind == OPTIONAL;
    }

    public boolean isProhibited() {
        return kind == PROHIBITED;
    }

    public boolean isOtherwise() {
        return isOtherwise;
    }

}

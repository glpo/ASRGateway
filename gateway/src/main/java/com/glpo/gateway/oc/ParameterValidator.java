package com.glpo.gateway.oc;

import java.util.Collection;
import java.util.List;

import org.jdom2.Element;

import com.glpo.gateway.persistance.model.AsrForm;

public interface ParameterValidator {
    public String validate(String formName, String attrName, ValidationHelper helper);
    public Collection<String> loadConfiguration(List<Element> list, String asrForm);

}

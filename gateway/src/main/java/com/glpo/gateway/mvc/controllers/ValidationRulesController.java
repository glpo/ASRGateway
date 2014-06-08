package com.glpo.gateway.mvc.controllers;

import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateErrorMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateSuccessMessageForOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glpo.gateway.oc.ValidationConfiguration;
import com.glpo.gateway.oc.validation.genericholder.ValidatorHolder;
import com.glpo.gateway.persistance.model.User;

@Controller
@RequestMapping(value = "/admin")
public class ValidationRulesController {
    @Autowired
    private ValidationConfiguration validationConfig;

    private ValidatorHolder validatorHolder;

    @RequestMapping(value = "/validationRules", method = RequestMethod.GET)
    public String init(ModelMap map) {
	try {
	    validatorHolder = new ValidatorHolder();
	    map.put("holder", validatorHolder);
	    map.put("validators", validationConfig.getValidatorsNamesWithAttributes());
	    return "admin/validationRules";
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    @RequestMapping(value = "/addValidationRule", method = RequestMethod.POST)
    public String addValidationRule(@ModelAttribute("holder") ValidatorHolder holder, ModelMap map) {
	System.out.println(holder.getValidatorName());
	System.out.println(holder.getValidatorClass());
	System.out.println(holder.getTargetAttributes());
	System.out.println(holder.getRequired());
	System.out.println(holder.getOptional());
	System.out.println(holder.getProhibited());
	
	String required = holder.getRequired();
	String optional = holder.getOptional();
	String prohibited = holder.getProhibited();
	
	if((required == null || required.isEmpty()) && (optional == null || optional.isEmpty()) && (prohibited == null ||prohibited.isEmpty())) {
	    map.put("message", generateErrorMessageForOutput("One of the Required/Optional/Prohibited clauses sholdn't be empty!"));
	    return "admin/validationRules";
	}
	
	map.put("message", generateSuccessMessageForOutput("New Validation Rule has been added"));
	map.put("validators", validationConfig.getValidatorsNamesWithAttributes());
	return "admin/validationRules";
    }

}

package com.glpo.gateway.mvc.controllers;

import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateErrorMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateInformationMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateSuccessMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.getAllFormAttributesForOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrFormType;
import com.glpo.gateway.persistance.model.Customer;
import com.glpo.gateway.service.AsrAttributeService;
import com.glpo.gateway.service.AsrFormTypeService;
import com.glpo.gateway.service.CustomerService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AsrAttributeService asrAttributeService;
    @Autowired
    AsrFormTypeService asrFormTypeService;

    private AsrAttribute asrAttr;

    @RequestMapping(value = "/addAttribute", method = RequestMethod.GET)
    public String initAttribute(ModelMap map) {
	asrAttr = new AsrAttribute();
	map.put("asrAttr", asrAttr);
	map.put("attributes", getAllFormAttributesForOutput(asrAttributeService.getAllAttributes()));
	return "admin/newAttribute";
    }

    @RequestMapping(value = "/addAttribute", method = RequestMethod.POST)
    public String addAttribute(@ModelAttribute("asrAttr") AsrAttribute asrAttr,
	    @RequestParam("formType") String formType, ModelMap map) {

	if (asrAttr != null) {
	    AsrFormType asrFormType = asrFormTypeService.getAsrFormTypeByName(formType);
	    if (asrFormType != null) {
		if (asrAttributeService.isAttributeExists(asrFormType, asrAttr.getName())) {
		    map.put("message", generateInformationMessageForOutput("Attribute: " + asrAttr.getName()
			    + " already exists on form: " + asrFormType.getFormType()));
		    map.put("attributes", getAllFormAttributesForOutput(asrAttributeService.getAllAttributes()));
		    return "admin/newAttribute";
		} else {
		    asrAttr.setAsrFormType(asrFormType);
		    asrAttributeService.addAttribute(asrAttr);
		    map.put("message", generateSuccessMessageForOutput("New attribute has been created"));
		    map.put("attributes", getAllFormAttributesForOutput(asrAttributeService.getAllAttributes()));
		    return "admin/newAttribute";
		}
	    }
	}
	map.put("message", generateErrorMessageForOutput("Internal error while attribute creating"));
	return "admin/newAttribute";
    }
}

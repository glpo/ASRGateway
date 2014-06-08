package com.glpo.gateway.mvc.controllers;

import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateErrorMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateSuccessMessageForOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glpo.gateway.persistance.model.AsrForm;
import com.glpo.gateway.service.AsrFormService;

@Controller
public class RequestsController {

    @Autowired
    private AsrFormService asrFormService;

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public String init(ModelMap map) {
	map.put("requests", asrFormService.getAllAsrFormsForOutput());
	return "requests";
    }

    @RequestMapping(value = "/viewRequest", method = RequestMethod.POST)
    public String viewRequest(@RequestParam("id") String id, ModelMap map) {
	map.put("message", id);
	map.put("requests", asrFormService.getAllAsrFormsForOutput());
	return "requests";
    }

    @RequestMapping(value = "/editRequest", method = RequestMethod.POST)
    public String editRequest(@RequestParam("id") String id, ModelMap map) {
	map.put("message", id);
	map.put("requests", asrFormService.getAllAsrFormsForOutput());
	return "requests";
    }

    @RequestMapping(value = "/cancelRequest", method = RequestMethod.POST)
    public String cancelRequest(@RequestParam("id") String id, ModelMap map) {	
	AsrForm form = asrFormService.getAsrFormById(id);
	if (form != null) {
	    if ("Canceled".equals(form.getStatus())) {
		map.put("message", generateErrorMessageForOutput("This Request has been already canceled!"));
		map.put("requests", asrFormService.getAllAsrFormsForOutput());
		return "requests";
	    }
	    form.setStatus("Canceled");
	    asrFormService.update(form);
	    
	    map.put("message", generateSuccessMessageForOutput("Request's status successfully changed to 'Canceled'"));
	    map.put("requests", asrFormService.getAllAsrFormsForOutput());
	    return "requests";
	}	
	map.put("message", generateErrorMessageForOutput("Internal Error occured!"));
	return "requests";
	
    }

}

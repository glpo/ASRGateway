package com.glpo.gateway.mvc.controllers;


import static com.glpo.gateway.oc.ValidationResultUtils.p;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.glpo.gateway.oc.ConfigurationReader;
import com.glpo.gateway.oc.RequestValidator;

import com.glpo.gateway.service.AsrFormService;


@Controller
public class FileUploadController {

    @Autowired
    private ConfigurationReader configReader;
    @Autowired
    private RequestValidator requestValidaor;
    @Autowired
    private AsrFormService asrFormService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String init() {
	return "fileUpload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file,
	    ModelMap map) {
	if (file.isEmpty()) {
	    map.put("message", ControllerHelper.generateErrorMessageForOutput("You failed to upload " + name
		    + " because the file was empty."));
	    return "fileUpload";
	} else {
	    try {
		File newAsrXml = new File(file.getOriginalFilename());
		file.transferTo(newAsrXml);

		Element configuration = configReader.readAsrXML(newAsrXml);
		Map<String, Map<String, String>> asrFormStructure = configReader.getAsrFormFromXML(configuration);

		List<String> errorMessages = requestValidaor.validte(asrFormStructure);

		if (errorMessages != null && errorMessages.size() > 0) {
		    StringBuilder validationResult = new StringBuilder();

		    for (String msg : errorMessages) {
			validationResult.append(p(msg));
		    }
		    map.put("message", validationResult.toString());
		    return "validationResults";
		}

		asrFormService.saveAsrRequest(asrFormStructure, "VirginTelecom");
		map.put("message", ControllerHelper.generateSuccessMessageForOutput("You successfully uploaded Request! It's in progress now!"));
		return "fileUpload";

	    } catch (IOException e) {
		map.put("message",
			ControllerHelper.generateErrorMessageForOutput("You failed to upload " + name + " => " + e.getMessage()));
		return "fileUpload";
	    } 
	}		
    }
}

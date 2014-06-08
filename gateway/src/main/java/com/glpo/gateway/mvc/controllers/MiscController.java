package com.glpo.gateway.mvc.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MiscController {
    
    @RequestMapping(value = "/getAsogVersion", method = RequestMethod.GET)
    public String getAsoVersion() {	
	return "standartInfo";
    }

}

package com.glpo.gateway.mvc.controllers;

import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateErrorMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateInformationMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateSuccessMessageForOutput;

import oracle.sql.CHAR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.glpo.gateway.mvc.exception.GatewayException;
import com.glpo.gateway.persistance.model.Customer;
import com.glpo.gateway.persistance.model.User;
import com.glpo.gateway.service.AuthenticateService;
import com.glpo.gateway.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private UserService userService;

    private User user;
    private Customer customer;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String init(ModelMap map) {
	user = new User();
	customer = new Customer();
	
	map.put("userData", user);
	map.put("customer", customer);
	
	return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("userData") User user, BindingResult bindingResult, ModelMap model) {
	ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "login", "login", "Username can not be empty.");
	ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "passwd", "passwd", "Password not be empty");
	if (bindingResult.hasErrors()) {
	    model.put("message", generateInformationMessageForOutput("Username/password fields are empty"));
	    return "login";
	} else {
	    System.out.println(user.getLogin() + " " + user.getPasswd());
	    if (user != null
		    && (authenticateService.verifyUserNameAndPassword(user.getLogin(), user.getPasswd()) 
			    || authenticateService.verifyCustomerNameAndPassword(user.getLogin(), user.getPasswd()))) {
		model.put("name", user.getLogin());
		return "success";
	    } else {
		model.put("message", generateErrorMessageForOutput("Invalid username/password"));
		return "login";
	    }
	}
    }   

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("customer") Customer customer, ModelMap map) {
	System.out.println(customer.getName());
	System.out.println(customer.getPasswd());
	System.out.println(customer.getEmail());
	System.out.println(customer.getCity());
	System.out.println(customer.getPhoneNumber());
	System.out.println(customer.getStreet());
	System.out.println(customer.getState());
	
	map.put("message", generateSuccessMessageForOutput("Registration completed successfully! Our operator contact You as soon as posible. Thanks."));
	
	return "customerRegistration";
    }
}

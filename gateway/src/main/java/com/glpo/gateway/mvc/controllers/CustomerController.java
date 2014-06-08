package com.glpo.gateway.mvc.controllers;

import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateErrorMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateInformationMessageForOutput;
import static com.glpo.gateway.mvc.controllers.ControllerHelper.generateSuccessMessageForOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.glpo.gateway.persistance.model.Customer;
import com.glpo.gateway.service.CustomerService;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    private Customer customer;

    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String initCustomer(ModelMap map) {
	customer = new Customer();
	map.put("customer", customer);
	return "admin/newCustomer";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute("customer") Customer newCustomer, ModelMap map) {
	if (newCustomer != null) {
	    customerService.addCustomer(newCustomer);
	    map.put("message", generateSuccessMessageForOutput("New Customer has been created"));
	    return "admin/newCustomer";
	}
	map.put("message", generateErrorMessageForOutput("Internal error while customer creating!"));
	return "admin/newCustomer";
    }

    @RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
    public String customers(ModelMap map) {
	map.put("customers", customerService.getAllCustomersForOutput());

	return "customers";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public String activateCustomers(@RequestParam("id") String id, ModelMap map) {
	Customer customer = customerService.findCustomerById(id);
	if (customer != null) {
	    if (customer.getIsActive() == 1) {
		map.put("message", generateInformationMessageForOutput("This Customer is already 'Active'"));
		map.put("customers", customerService.getAllCustomersForOutput());
		return "customers";
	    } else {
		customer.setIsActive(1);
		customerService.update(customer);
		map.put("message", generateSuccessMessageForOutput("Customer Activated Successfully!"));
		map.put("customers", customerService.getAllCustomersForOutput());
		return "customers";
	    }
	}
	map.put("message", generateErrorMessageForOutput("Internal Error Occured"));
	map.put("customers", customerService.getAllCustomersForOutput());
	return "customers";
    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.POST)
    public String deactivateCustomers(@RequestParam("id") String id, ModelMap map) {
	Customer customer = customerService.findCustomerById(id);
	if (customer != null) {
	    if (customer.getIsActive() == 0) {
		map.put("message", generateInformationMessageForOutput("This Customer is already 'Deactivated'"));
		map.put("customers", customerService.getAllCustomersForOutput());
		return "customers";
	    } else {
		customer.setIsActive(0);
		customerService.update(customer);
		map.put("message", generateSuccessMessageForOutput("Customer Deactivated Successfully!"));
		map.put("customers", customerService.getAllCustomersForOutput());
		return "customers";
	    }
	}
	map.put("message", generateErrorMessageForOutput("Internal Error Occured"));
	map.put("customers", customerService.getAllCustomersForOutput());

	return "customers";
    }

}

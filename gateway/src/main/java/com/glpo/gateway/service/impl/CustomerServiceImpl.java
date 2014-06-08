package com.glpo.gateway.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glpo.gateway.mvc.controllers.ControllerHelper;
import com.glpo.gateway.persistance.dao.CustomerDAO;
import com.glpo.gateway.persistance.model.Customer;
import com.glpo.gateway.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public void addCustomer(Customer customer) {
	customerDAO.create(customer);

    }

    public void removeCustomer(Customer customer) {
	customerDAO.remove(customer);
    }
    
    public void update(Customer customer) {
	customerDAO.update(customer);
    }

    public Customer getCustomerByName(String name) {
	return customerDAO.getCustomerByName(name);
    }

    public List<String> getAllCustomersForOutput() {
	List<Customer> customers = customerDAO.getAllCustomers();

	if (customers != null) {
	    List<String> result = new ArrayList<String>();
	    int i = 0;

	    for (Customer customer : customers) {
		result.add("<tr><td width=\"15%\">" + getOperationButtons(String.valueOf(customer.getCustomerId())) + "</td> " +
			"<td>" + (i + 1) + "</td> " +
			"<td>" + customer.getName() + "</td> " +
			"<td>" + customer.getPhoneNumber() + "</td>" +
			"<td>" + customer.getEmail() + "</td>" +
			"<td>" + customer.getState() + "</td>" +
			"<td>" + customer.getCity() + "</td>" +
			"<td>" + customer.getStreet() + "</td>" +
			"<td>" + ControllerHelper.getCustomerStatus(customer.getIsActive()) + "</td>" +
			"</tr>");
		i++;
	    }
	    return result;
	}

	return null;
    }
    
    private String getOperationButtons(String customerId) {
   	return "<div class=\"row\">" +
   			"<div class=\"col-lg-1\" style=\"margin-right: 55px\"><form role=\"form\" action=\"/gateway/customers/activate\" method=\"POST\"> <input type=\"hidden\" name=\"id\" value=\"" + customerId+ " \"> <input type=\"submit\" class=\"btn btn-success\" value=\"Activate\">   </form></div>" +   			
   			"<div class=\"col-lg-2\"><form role=\"form\" action=\"/gateway/customers/deactivate\" method=\"POST\"> <input type=\"hidden\" name=\"id\" value=\"" + customerId+ " \"> <input type=\"submit\" class=\"btn btn-danger\" value=\"Deactivate\">   </form></div>" +   			
   		"</div>";
    }

    public Customer findCustomerById(String id) {
	return customerDAO.findCustomerById(id);
    }

}

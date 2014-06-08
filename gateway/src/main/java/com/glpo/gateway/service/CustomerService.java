package com.glpo.gateway.service;

import java.util.List;

import com.glpo.gateway.persistance.model.Customer;

public interface CustomerService {
	public void addCustomer(Customer customer);
	public void removeCustomer(Customer customer);
	public void update(Customer customer);
	public Customer getCustomerByName(String name);
	public Customer findCustomerById(String id);
	public List<String> getAllCustomersForOutput();
}

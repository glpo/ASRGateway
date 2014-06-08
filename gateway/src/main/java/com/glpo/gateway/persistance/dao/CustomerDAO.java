package com.glpo.gateway.persistance.dao;

import java.util.List;

import com.glpo.gateway.persistance.model.Customer;

public interface CustomerDAO {
	public void create(Customer customer);	
	public void remove(Customer customer);
	public void remove(Integer id);
	public void update(Customer customer);
	public Customer getCustomerByName(String name);
	public Customer findCustomerById(String id);
	public List<Customer> getAllCustomers();
}

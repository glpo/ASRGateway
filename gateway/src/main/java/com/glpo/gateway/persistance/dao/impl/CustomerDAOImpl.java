package com.glpo.gateway.persistance.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.CustomerDAO;
import com.glpo.gateway.persistance.model.Customer;

@Repository
@Transactional
public class CustomerDAOImpl extends AbstractDAO<Customer> implements CustomerDAO {

    public CustomerDAOImpl() {
	super(Customer.class);
    }

    public void remove(Integer id) {
	Customer customer = (Customer) getSession().load(Customer.class, id);
	if (null != customer) {
	    getSession().delete(customer);
	}
    }
    
    public void update(Customer customer) {
	getSession().update(customer);
    }

    public Customer getCustomerByName(String name) {
	Query query = getSession().createQuery("from Customer where name = :name");
	query.setParameter("name", name);

	List result = query.list();
	if (result != null && result.size() > 0) {
	    return (Customer) result.get(0);
	}
	return null;
    }

    public List<Customer> getAllCustomers() {
	Query query = getSession().createQuery("from Customer");
	return query.list();
    }

    public Customer findCustomerById(String id) {
	Query query = getSession().createQuery("from Customer where customerId = :id");
	query.setParameter("id", Long.parseLong(id.trim()));

	List result = query.list();
	if (result != null && result.size() > 0) {
	    return (Customer) result.get(0);
	}
	return null;
    }
}

package com.glpo.gateway.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.glpo.gateway.persistance.model.Customer;
import com.glpo.gateway.persistance.model.User;
import com.glpo.gateway.service.AuthenticateService;


@Service
public class AuthenticateServiceImpl implements AuthenticateService {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public boolean isUserNameExists(String userName) {
		Locale.setDefault(Locale.ENGLISH);
		List<User> users = (List<User>) hibernateTemplate.find("from User u where u.login = ?", userName);
		if(users.size() != 0) {
			return true;
		}
		return false;		
	}

	public boolean verifyUserNameAndPassword(String userName, String password) {
		Locale.setDefault(Locale.ENGLISH);
		List<User> users = (List<User>) hibernateTemplate.find("from User u where u.login = ? and u.passwd = ?", userName, password);
		if(users.size() != 0) {
			return true;
		}
		return false;
	}

	public boolean verifyCustomerNameAndPassword(String customerName, String passwd) {
	    Locale.setDefault(Locale.ENGLISH);
	    List<Customer> customers = (List<Customer>) hibernateTemplate.find("from Customer c where c.name = ? and c.passwd = ?", customerName, passwd);
	    
	    if(customers != null && customers.size() > 0) {
		return true;
	    }
	    return false;
	}

}

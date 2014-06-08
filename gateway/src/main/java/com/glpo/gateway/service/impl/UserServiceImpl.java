package com.glpo.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glpo.gateway.persistance.dao.UserDAO;
import com.glpo.gateway.persistance.model.User;
import com.glpo.gateway.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	public void addUser(User user) {		
		userDAO.create(user);
	}

	public void removeUser(User user) {	
		userDAO.remove(user);
	}

	public boolean isEmailValid(String email) {
		if (email != null) {
            return email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        }
        return false;
	}

	public boolean isNameValid(String name) {
		if (name != null) {
            return name.matches("(?i)[a-z0-9 ']{1,32}");
        }
        return false;
	}

}

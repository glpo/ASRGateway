package com.glpo.gateway.persistance.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.UserDAO;
import com.glpo.gateway.persistance.model.User;

@Repository
@Transactional
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {	
	
	public UserDAOImpl() {
		super(User.class);		
	}

	public void remove(Integer id) {
		User user = (User) getSession().load(User.class, id);
		if(null != user) {
			getSession().delete(user);
		}		
	}
}

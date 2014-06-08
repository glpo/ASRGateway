package com.glpo.gateway.persistance.dao;

import com.glpo.gateway.persistance.model.User;

public interface UserDAO {
	public void create(User user);	
	public void remove(User user);
	public void remove(Integer id);
}

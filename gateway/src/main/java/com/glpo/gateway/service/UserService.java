package com.glpo.gateway.service;

import com.glpo.gateway.persistance.model.User;

public interface UserService {
	public void addUser(User user);
	public void removeUser(User user);
	public boolean isEmailValid(String email);    
    public boolean isNameValid(String name);
}

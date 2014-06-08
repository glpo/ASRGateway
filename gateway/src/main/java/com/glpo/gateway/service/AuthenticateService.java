package com.glpo.gateway.service;

public interface AuthenticateService {
	public boolean isUserNameExists(String userName);
	public boolean verifyUserNameAndPassword(String userName, String password);
	public boolean verifyCustomerNameAndPassword(String customerName, String passwd);
}

package com.glpo.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glpo.gateway.persistance.dao.GroupDAO;
import com.glpo.gateway.persistance.model.Group;
import com.glpo.gateway.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	private GroupDAO groupDAO;

	public void addGroup(Group group) {
		groupDAO.create(group);		
	}

	public void removeGroup(Group group) {
		groupDAO.remove(group);		
	}

}

package com.glpo.gateway.persistance.dao;

import com.glpo.gateway.persistance.model.Group;

public interface GroupDAO {
	public void create(Group group);	
	public void remove(Group group);
	public void remove(Integer id);
}

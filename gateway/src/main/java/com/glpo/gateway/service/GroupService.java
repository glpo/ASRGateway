package com.glpo.gateway.service;

import com.glpo.gateway.persistance.model.Group;

public interface GroupService {
	public void addGroup(Group group);
	public void removeGroup(Group group);
}

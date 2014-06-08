package com.glpo.gateway.persistance.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.GroupDAO;
import com.glpo.gateway.persistance.model.Group;

@Repository
@Transactional
public class GroupDAOImpl extends AbstractDAO<Group> implements GroupDAO {	
	
	public GroupDAOImpl() {
		super(Group.class);		
	}

	public void remove(Integer id) {
		Group group = (Group) getSession().load(Group.class, id);
		if(null != group) {
			getSession().delete(group);
		}		
	}
}

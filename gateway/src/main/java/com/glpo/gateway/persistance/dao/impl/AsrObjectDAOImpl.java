package com.glpo.gateway.persistance.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.AsrObjectDAO;
import com.glpo.gateway.persistance.model.AsrObject;

@Repository
@Transactional
public class AsrObjectDAOImpl extends AbstractDAO<AsrObject> implements AsrObjectDAO {	
	
	public AsrObjectDAOImpl() {
		super(AsrObject.class);		
	}

	public void remove(Integer id) {
		AsrObject object = (AsrObject) getSession().load(AsrObject.class, id);
		if(null != object) {
			getSession().delete(object);
		}		
	}
}

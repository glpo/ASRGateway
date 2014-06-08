package com.glpo.gateway.persistance.dao;

import com.glpo.gateway.persistance.model.AsrObject;

public interface AsrObjectDAO {
	public void create(AsrObject object);	
	public void remove(AsrObject object);
	public void remove(Integer id);
}

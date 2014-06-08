package com.glpo.gateway.persistance.dao;

import java.util.List;

import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrFormType;

public interface AsrAttributeDAO {	
	public void create(AsrAttribute attribute);
	public void remove(AsrAttribute attribute);
	public void remove(Integer id);
	public List<AsrAttribute> getAllAttributes();
	public AsrAttribute getAsrAttributeForFormByName(AsrFormType asrFormType, String name);
	public boolean isAttributeExists(String attrName);
	public boolean isAttributeExists(AsrFormType asrFormType, String attrName);
}

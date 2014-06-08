package com.glpo.gateway.persistance.dao;

import java.util.List;

import com.glpo.gateway.persistance.model.AsrFormType;

public interface AsrFormTypeDAO {
	public void create(AsrFormType formType);
	public void remove(AsrFormType formType);
	public void remove(Integer id);
	public AsrFormType getAsrFormTypeByName(String typeName);
	public List<AsrFormType> getAllAsrFormTypes();
}

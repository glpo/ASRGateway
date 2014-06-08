package com.glpo.gateway.service;

import java.util.List;

import com.glpo.gateway.persistance.model.AsrFormType;

public interface AsrFormTypeService {
	public void addAsrFormType(AsrFormType formType);
	public void removeAsrFormType(AsrFormType formType);
	public AsrFormType getAsrFormTypeByName(String typeName);
	public List<AsrFormType> getAllAsrForms();
}

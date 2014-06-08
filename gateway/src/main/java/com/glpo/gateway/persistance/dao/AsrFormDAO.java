package com.glpo.gateway.persistance.dao;


import java.util.List;

import com.glpo.gateway.persistance.model.AsrForm;

public interface AsrFormDAO {	
	public void create(AsrForm form);
	public void remove(AsrForm form);
	public void remove(Integer id);	
	public List<AsrForm> getAllAsrForms();
	public AsrForm getAsrFormById(String id);
	public void update(AsrForm form);
	public List<AsrForm> getAsrRequestByMainFormId(String id);
}

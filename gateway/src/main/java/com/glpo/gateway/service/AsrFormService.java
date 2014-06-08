package com.glpo.gateway.service;

import java.util.List;
import java.util.Map;

import com.glpo.gateway.persistance.model.AsrForm;

public interface AsrFormService {
	public void addAsrForm(AsrForm form);
	public void removeAsrForm(AsrForm form);
	public void saveAsrRequest(Map<String, Map<String, String>> asrFormsStructure, String customerName);
	public List<String> getAllAsrFormsForOutput();
	public AsrForm getAsrFormById(String id);
	public String getStatusByAsrFormId(String id);
	public void update(AsrForm form);
	//public 
}

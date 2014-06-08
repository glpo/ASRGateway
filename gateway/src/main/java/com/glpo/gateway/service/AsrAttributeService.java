package com.glpo.gateway.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrFormType;

@Transactional
public interface AsrAttributeService {
	public void addAttribute(AsrAttribute attr);
	public void removeAttribute(AsrAttribute attr);
	public void removeAttribute(Integer id);
	public Map<String, List<String>> getAllAttributes();
	public AsrAttribute getAsrAttributeForFormByName(AsrFormType asrFormType, String name);
	public boolean isAttributeExists(String attrName);
	public boolean isAttributeExists(AsrFormType asrFormType, String attrName);
}

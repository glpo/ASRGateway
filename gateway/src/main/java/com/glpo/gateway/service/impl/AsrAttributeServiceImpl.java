package com.glpo.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.AsrAttributeDAO;
import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrFormType;
import com.glpo.gateway.service.AsrAttributeService;
import com.glpo.gateway.service.AsrFormTypeService;

@Service
@Transactional
public class AsrAttributeServiceImpl implements AsrAttributeService {

    @Autowired
    private AsrAttributeDAO asrAttributeDAO;
    @Autowired
    private AsrFormTypeService asrFormTypeService;

    public void addAttribute(AsrAttribute attr) {
	asrAttributeDAO.create(attr);
    }

    public void removeAttribute(AsrAttribute attr) {
	asrAttributeDAO.remove(attr);
    }

    public void removeAttribute(Integer id) {
	asrAttributeDAO.remove(id);
    }

    public AsrAttribute getAsrAttributeForFormByName(AsrFormType asrFormType, String name) {
	return asrAttributeDAO.getAsrAttributeForFormByName(asrFormType, name);
    }

    public boolean isAttributeExists(String attrName) {
	return asrAttributeDAO.isAttributeExists(attrName);
    }

    public boolean isAttributeExists(AsrFormType asrFormType, String attrName) {
	return asrAttributeDAO.isAttributeExists(asrFormType, attrName);
    }

    public Map<String, List<String>> getAllAttributes() {
	Map<String, List<String>> asrFormAttrNameMap = new HashMap<String, List<String>>();
	List<AsrFormType> formTypes = asrFormTypeService.getAllAsrForms();
	if (formTypes != null && !formTypes.isEmpty()) {
	    for (AsrFormType formType : formTypes) {
		List<String> attrNames = new ArrayList<String>();
		List<AsrAttribute> formAttributes = formType.getAsrAttributes();
		if (formAttributes != null && !formAttributes.isEmpty()) {
		    for (AsrAttribute attr : formAttributes) {
			attrNames.add(attr.getName());
		    }
		    asrFormAttrNameMap.put(formType.getFormType(), attrNames);
		}
	    }
	}
	return asrFormAttrNameMap;
    }

}

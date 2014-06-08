package com.glpo.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glpo.gateway.persistance.dao.AsrAttributeDAO;
import com.glpo.gateway.persistance.dao.AsrFormDAO;
import com.glpo.gateway.persistance.dao.AttributeValueDAO;
import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrForm;
import com.glpo.gateway.persistance.model.AttributeValue;
import com.glpo.gateway.service.AsrAttributeService;
import com.glpo.gateway.service.AsrFormService;
import com.glpo.gateway.service.AttributeValueService;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {
	
	@Autowired
	private AttributeValueDAO attributeValueDAO;
	@Autowired
	private AsrAttributeDAO asrAttrDAO;
	@Autowired
	private AsrFormDAO asrFormDAO;
	
	public void addAttributeValue(AttributeValue attrValue) {
		attributeValueDAO.create(attrValue);		
	}

	public void removeAttributeValue(AttributeValue attrValue) {
		attributeValueDAO.remove(attrValue);		
	}

	public String getValueFromFormForAttr(AsrForm form, String attrName) {
	    AsrAttribute asrAttr = asrAttrDAO.getAsrAttributeForFormByName(form.getAsrFormType(), attrName);
	    AttributeValue attrValue = attributeValueDAO.getValueFromFormForAttr(asrAttr, form);
	    return attrValue.getValue();
	}	

}

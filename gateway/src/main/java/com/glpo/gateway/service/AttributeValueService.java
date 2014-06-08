package com.glpo.gateway.service;

import com.glpo.gateway.persistance.model.AsrForm;
import com.glpo.gateway.persistance.model.AttributeValue;

public interface AttributeValueService {
	public void addAttributeValue(AttributeValue attrValue);
	public void removeAttributeValue(AttributeValue attrValue);
	public String getValueFromFormForAttr(AsrForm form, String attr);
}

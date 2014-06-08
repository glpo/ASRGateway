package com.glpo.gateway.persistance.dao;

import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrForm;
import com.glpo.gateway.persistance.model.AttributeValue;

public interface AttributeValueDAO {
	public void create(AttributeValue value);	
	public void remove(AttributeValue value);
	public void remove(Integer id);
	public AttributeValue getValueFromFormForAttr(AsrAttribute attr, AsrForm asrForm);
}

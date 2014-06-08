package com.glpo.gateway.persistance.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.AttributeValueDAO;
import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrForm;
import com.glpo.gateway.persistance.model.AttributeValue;

@Repository
@Transactional
public class AttributeValueDAOImpl extends AbstractDAO<AttributeValue> implements AttributeValueDAO{	
	
	public AttributeValueDAOImpl() {
		super(AttributeValue.class);		
	}

	public void remove(Integer id) {
		AttributeValue attrValue = (AttributeValue) getSession().load(AttributeValue.class, id);
		if(null != attrValue) {
			getSession().delete(attrValue);
		}		
	}

	public AttributeValue getValueFromFormForAttr(AsrAttribute attr, AsrForm asrForm) {
	   Query query = getSession().createQuery("from AttributeValue where asrAttribute = :asrAttr and asrForm = :asrForm");
	   query.setParameter("asrAttr", attr);
	   query.setParameter("asrForm", asrForm);
	   
	   List result = query.list();
	   
	   if(result != null && result.size() > 0){
	       return (AttributeValue) result.get(0);
	   }
	    return null;
	}	
}

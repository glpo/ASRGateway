package com.glpo.gateway.persistance.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.AsrAttributeDAO;
import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrFormType;

@Repository
public class AsrAttributeDAOImpl extends AbstractDAO<AsrAttribute> implements AsrAttributeDAO {

	public AsrAttributeDAOImpl() {
		super(AsrAttribute.class);		
	}

	public void remove(Integer id) {
		AsrAttribute attr = (AsrAttribute) getSession().load(AsrAttribute.class, id);
		if(null != attr) {
			getSession().delete(attr);
		}		
	}

	public AsrAttribute getAsrAttributeForFormByName(AsrFormType asrFormType, String name) {
	    Query query = getSession().createQuery("from AsrAttribute where name = :name and asrFormType = :formType");
	    query.setParameter("name", name);
	    query.setParameter("formType", asrFormType);
	    
	    List result = query.list();
	    if(result != null && result.size() > 0) {
		return (AsrAttribute) result.get(0);
	    }	    
	    return null;
	}

	public boolean isAttributeExists(String attrName) {
	    Query query = getSession().createQuery("from AsrAttribute where name = :name");
	    query.setParameter("name", attrName);
	    
	    List result = query.list();
	    if(result != null && result.size() > 0) {
		return true;
	    }
	    return false;
	}

	public boolean isAttributeExists(AsrFormType asrFormType, String attrName) {
	    Query query = getSession().createQuery("from AsrAttribute where name = :name and asrFormType = :formType");
	    query.setParameter("name", attrName);
	    query.setParameter("formType", asrFormType);
	    
	    List result = query.list();
	    if(result != null && result.size() > 0) {
		return true;
	    }	   
	    return false;
	}

	public List<AsrAttribute> getAllAttributes() {
	    Query query = getSession().createQuery("from AsrAttribute");
	    
	    List result = query.list();		    
	    return result;
	}
}

package com.glpo.gateway.persistance.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.AsrFormTypeDAO;
import com.glpo.gateway.persistance.model.AsrFormType;

@Repository
public class AsrFormTypeDAOImpl extends AbstractDAO<AsrFormType> implements AsrFormTypeDAO {	
	
	public AsrFormTypeDAOImpl() {
		super(AsrFormType.class);		
	}

	public void remove(Integer id) {
		AsrFormType formType = (AsrFormType) getSession().load(AsrFormType.class, id);
		if(null != formType) {
			getSession().delete(formType);
		}		
	}

	public AsrFormType getAsrFormTypeByName(String typeName) {
	    Query query = getSession().createQuery("from AsrFormType where formType = :type ");
	    query.setParameter("type", typeName);
	    
	    List result = query.list();
	    if(result != null && result.size() > 0) {
		return (AsrFormType) result.get(0);
	    }
	    return null;
	}

	public List<AsrFormType> getAllAsrFormTypes() {
	    Query query = getSession().createQuery("from AsrFormType");	    
	    
	    List result = query.list();
	    return result;
	}
}

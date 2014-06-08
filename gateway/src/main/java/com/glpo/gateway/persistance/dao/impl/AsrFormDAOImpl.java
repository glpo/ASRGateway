package com.glpo.gateway.persistance.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.glpo.gateway.persistance.dao.AsrFormDAO;
import com.glpo.gateway.persistance.model.AsrForm;

@Repository
public class AsrFormDAOImpl extends AbstractDAO<AsrForm> implements AsrFormDAO {

    public AsrFormDAOImpl() {
	super(AsrForm.class);
    }

    public void remove(Integer id) {
	AsrForm form = (AsrForm) getSession().load(AsrForm.class, id);
	if (null != form) {
	    getSession().delete(form);
	}
    }

    public List<AsrForm> getAllAsrForms() {
	Query query = getSession().createQuery("from AsrForm where mainForm = 0");
	
	List<AsrForm> result = (List<AsrForm>) query.list();
	return result;
    }

    public AsrForm getAsrFormById(String id) {
	Query query = getSession().createQuery("from AsrForm where asrFormId = :id");
	query.setParameter("id", Long.parseLong(id.trim()));
	
	List<AsrForm> result = query.list();
	if(result != null && result.size() > 0) {
	    return result.get(0);
	}
	return null;
    }
    
    public void update(AsrForm form) {
	getSession().update(form);
    }

    public List<AsrForm> getAsrRequestByMainFormId(String id) {
	AsrForm asrForm  = getAsrFormById(id);
	if(asrForm != null) {
	    Query query = getSession().createQuery("from AsrForm where mainForm = :id");
	    query.setParameter("id", Long.parseLong(id.trim()));
	    
	    List<AsrForm> result = query.list();
	    if(result != null && result.size() > 0) {
		result.add(asrForm);
		return result;
	    }
	}	
	return null;
    }
}

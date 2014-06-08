package com.glpo.gateway.persistance.dao.impl;

import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class AbstractDAO<T> implements InitializingBean {
	private Class<T> entityClass;	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public AbstractDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void create(T entity) {
		getSession().save(entity);		
	}
	
	public void update(T entity) {
		getSession().update(entity);
	}
	
	public T findById(Integer id) {
		if(id != null) {
			return (T) getSession().load(entityClass, id);
		}
		return null;
	}
	
	public void remove(T entity) {
		getSession().delete(entity);		
	}
	
	public void afterPropertiesSet() throws Exception {
	    Locale.setDefault(Locale.US);
	}
}

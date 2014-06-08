package com.glpo.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glpo.gateway.persistance.dao.AsrObjectDAO;
import com.glpo.gateway.persistance.model.AsrObject;
import com.glpo.gateway.service.AsrObjectService;

@Service
public class AsrObjectServiceImpl implements AsrObjectService {
	
	@Autowired
	private AsrObjectDAO asrObjectDAO;

	public void addAsrObject(AsrObject object) {
		asrObjectDAO.create(object);		
	}

	public void removeAsrObject(AsrObject object) {
		asrObjectDAO.remove(object);		
	}

}

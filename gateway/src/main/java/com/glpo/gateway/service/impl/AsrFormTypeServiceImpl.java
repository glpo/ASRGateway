package com.glpo.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.persistance.dao.AsrFormTypeDAO;
import com.glpo.gateway.persistance.model.AsrFormType;
import com.glpo.gateway.service.AsrFormTypeService;

@Service
@Transactional
public class AsrFormTypeServiceImpl implements AsrFormTypeService {

    @Autowired
    private AsrFormTypeDAO asrFormTypeDAO;

    public void addAsrFormType(AsrFormType formType) {
	asrFormTypeDAO.create(formType);
    }

    public void removeAsrFormType(AsrFormType formType) {
	asrFormTypeDAO.remove(formType);
    }

    public AsrFormType getAsrFormTypeByName(String typeName) {
	return asrFormTypeDAO.getAsrFormTypeByName(typeName);
    }

    public List<AsrFormType> getAllAsrForms() {	
	return asrFormTypeDAO.getAllAsrFormTypes();
    }

}

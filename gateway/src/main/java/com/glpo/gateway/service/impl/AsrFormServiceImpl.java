package com.glpo.gateway.service.impl;

import static com.glpo.gateway.mvc.controllers.ControllerHelper.getStatusMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glpo.gateway.mvc.controllers.ControllerHelper;
import com.glpo.gateway.mvc.exception.GatewayException;
import com.glpo.gateway.persistance.dao.AsrFormDAO;
import com.glpo.gateway.persistance.model.AsrAttribute;
import com.glpo.gateway.persistance.model.AsrForm;
import com.glpo.gateway.persistance.model.AsrFormType;
import com.glpo.gateway.persistance.model.AttributeValue;
import com.glpo.gateway.service.AsrAttributeService;
import com.glpo.gateway.service.AsrFormService;
import com.glpo.gateway.service.AsrFormTypeService;
import com.glpo.gateway.service.AttributeValueService;
import com.glpo.gateway.service.CustomerService;

@Service
@Transactional
public class AsrFormServiceImpl implements AsrFormService {

    @Autowired
    private AsrFormTypeService asrFormTypeService;
    @Autowired
    private AttributeValueService attrValueService;
    @Autowired
    private AsrAttributeService asrAttrService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AsrFormDAO asrFormDAO;

    public void addAsrForm(AsrForm form) {
	asrFormDAO.create(form);
    }

    public void removeAsrForm(AsrForm form) {
	asrFormDAO.remove(form);
    }

    public void saveAsrRequest(Map<String, Map<String, String>> asrFormsStructure, String customerName) {
	List<AsrForm> createdForms = new ArrayList<AsrForm>();
	AsrForm asrForm = null;

	for (String form : asrFormsStructure.keySet()) {
	    AsrFormType asrFormType = asrFormTypeService.getAsrFormTypeByName(form);

	    AsrForm newForm = new AsrForm();
	    if ("ASR".equals(form)) {
		asrForm = newForm;
	    } else if (asrForm != null) {
		newForm.setMainForm(asrForm.getAsrFormId());
	    }
	    newForm.setAsrFormType(asrFormType);
	    newForm.setSubmitionDate(new Date());
	    newForm.setCustomer(customerService.getCustomerByName(customerName));
	    addAsrForm(newForm);

	    Map<String, String> attrNameToValue = asrFormsStructure.get(form);

	    for (String attr : attrNameToValue.keySet()) {
		AttributeValue newAttrValue = new AttributeValue();
		newForm.addAttributeValue(newAttrValue);

		AsrAttribute asrAttr = asrAttrService.getAsrAttributeForFormByName(asrFormType, attr);
		if (asrAttr == null) {
		    System.out.println("-------> " + attr);
		    throw new GatewayException("Asr Attribute " + attr
			    + " NOT exists in the system! Please, contact administrator.");
		}
		newAttrValue.setAsrAttribute(asrAttr);
		newAttrValue.setValue(attrNameToValue.get(attr));

		attrValueService.addAttributeValue(newAttrValue);
	    }
	    createdForms.add(newForm);
	}
    }

    public List<String> getAllAsrFormsForOutput() {
	List<AsrForm> asrForms = asrFormDAO.getAllAsrForms();

	if (asrForms != null) {
	    List<String> result = new ArrayList<String>();
	    int i = 0;
	    for (AsrForm form : asrForms) {
		result.add("<tr><td width=\"20%\">" + getOperationButtons(String.valueOf(form.getAsrFormId())) + "</td> <td>" + (i + 1) + "</td> <td>" + form.getCustomer().getName() + "</td> <td>"
			+ form.getSubmitionDate() + "</td><td>"
			+ attrValueService.getValueFromFormForAttr(form, "REQTYP1") + "</td><td>" + getStatusMessage(form.getStatus()) + "</td></tr>");
		i++;
	    }
	    return result;
	}
	return null;
    }

    private String getOperationButtons(String asrFormId) {
	return "<div class=\"row\">" +
			"<div class=\"col-lg-1\" style=\"margin-right: 35px\"><form role=\"form\" action=\"/gateway/viewRequest\" method=\"POST\"> <input type=\"hidden\" name=\"id\" value=\"" + asrFormId+ " \"> <input type=\"submit\" class=\"btn btn-primary\" value=\"View\">   </form></div>" +
			"<div class=\"col-lg-2\" style=\"margin-right: 10px\"><form role=\"form\" action=\"/gateway/editRequest\" method=\"POST\"> <input type=\"hidden\" name=\"id\" value=\"" + asrFormId+ " \"> <input type=\"submit\" class=\"btn btn-primary\" value=\"Edit\">   </form></div>" +
			"<div class=\"col-lg-3\"><form role=\"form\" action=\"/gateway/cancelRequest\" method=\"POST\"> <input type=\"hidden\" name=\"id\" value=\"" + asrFormId+ " \"> <input type=\"submit\" class=\"btn btn-primary\" value=\"Cancel\"> </form></div>" +
		"</div>";
    }

    public AsrForm getAsrFormById(String id) {
	return asrFormDAO.getAsrFormById(id);
    }

    public String getStatusByAsrFormId(String id) {
	AsrForm asrForm = asrFormDAO.getAsrFormById(id);	
	if(asrForm != null) {
	    return asrForm.getStatus();
	}
	return null;
    }

    public void update(AsrForm form) {
	asrFormDAO.update(form);	
    }
}

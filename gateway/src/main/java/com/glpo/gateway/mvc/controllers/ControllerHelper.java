package com.glpo.gateway.mvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.glpo.gateway.persistance.model.AsrForm;

public class ControllerHelper {

    public static List<String> getAllFormAttributesForOutput(Map<String, List<String>> formAttrNames) {
	if (formAttrNames != null && !formAttrNames.isEmpty()) {
	    List<String> result = new ArrayList<String>();
	    for (String form : formAttrNames.keySet()) {
		List<String> attrs = formAttrNames.get(form);
		for (int i = 0; i < attrs.size(); i++) {
		    result.add("<tr> <td>" + (i + 1) + "</td> <td>" + attrs.get(i) + "</td> <td>" + form
			    + "</td> <td>a long long attr description</td> </tr>");
		}
	    }
	    return result;
	}
	return null;
    }

    public static String generateErrorMessageForOutput(String errorMessageText) {
	return "<div class=\"alert alert-danger\"><strong>Error! </strong>" + errorMessageText + "</div>";
    }

    public static String generateSuccessMessageForOutput(String messageText) {
	return "<div class=\"alert alert-success\"><strong>Success. </strong>" + messageText + "</div>";
    }

    public static String generateInformationMessageForOutput(String messageText) {
	return "<div class=\"alert alert-info\"><strong>Information. </strong>" + messageText + "</div>";
    }

    public static String activeStatus() {
	return "<span class=\"label label-success\">Active</span>";
    }

    public static String notActiveStatus() {
	return "<span class=\"label label-warning\">Not Active</span>";
    }

    public static String enteringStatus() {
	return "<span class=\"label label-success\">Entering</span>";
    }

    public static String inProgressStatus() {
	return "<span class=\"label label-success\">In Progress</span>";
    }

    public static String canceledStatus() {
	return "<span class=\"label label-warning\">Canceled</span>";
    }

    public static String modifiedStatus() {
	return "<span class=\"label label-info\">Modified</span>";
    }

    public static String completedStatus() {
	return "<span class=\"label\">Completed</span>";
    }

    public static String getStatusMessage(String status) {
	if ("Entering".equals(status)) {
	    return enteringStatus();
	} else if ("Canceled".equals(status)) {
	    return canceledStatus();
	} else if ("Modified".equals(status)) {
	    return modifiedStatus();
	} else if ("Completed".equals(status)) {
	    return completedStatus();
	} else if ("In Progress".equals(status)) {
	    return inProgressStatus();
	}
	return null;
    }
    
    public static String getCustomerStatus(int status) {
	if(1 == status) {
	    return activeStatus();
	} else if (0 == status) {
	    return notActiveStatus();
	}
	return null;
    }

}

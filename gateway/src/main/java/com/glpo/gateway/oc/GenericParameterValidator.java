package com.glpo.gateway.oc;

import static com.glpo.gateway.oc.ValidationConfiguration.ADDITIONAL_PARAMS_TAG;
import static com.glpo.gateway.oc.ValidationConfiguration.ATTR_TAG;
import static com.glpo.gateway.oc.ValidationConfiguration.ERROR_MESSAGE_TAG_ATTR;
import static com.glpo.gateway.oc.ValidationConfiguration.FORM_TAG;
import static com.glpo.gateway.oc.ValidationConfiguration.ID_TAG_ATTR;
import static com.glpo.gateway.oc.ValidationConfiguration.OPERATOR_TAG_ATTR;
import static com.glpo.gateway.oc.ValidationConfiguration.OTHERWISE_TAG;
import static com.glpo.gateway.oc.ValidationConfiguration.TARGET_ATTRIBUTES_TAG;
import static com.glpo.gateway.oc.ValidationConfiguration.VALUE_TAG;
import static com.glpo.gateway.oc.validation.genericholder.Operator.EMPTY;
import static com.glpo.gateway.oc.validation.genericholder.Operator.EQ;
import static com.glpo.gateway.oc.validation.genericholder.Operator.EQUALS;
import static com.glpo.gateway.oc.validation.genericholder.Operator.GE;
import static com.glpo.gateway.oc.validation.genericholder.Operator.GT;
import static com.glpo.gateway.oc.validation.genericholder.Operator.IN;
import static com.glpo.gateway.oc.validation.genericholder.Operator.LE;
import static com.glpo.gateway.oc.validation.genericholder.Operator.LT;
import static com.glpo.gateway.oc.validation.genericholder.Operator.NOT_EMPTY;
import static com.glpo.gateway.oc.validation.genericholder.Operator.NOT_EQUALS;
import static com.glpo.gateway.oc.validation.genericholder.Operator.NOT_IN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jdom2.Element;

import com.glpo.gateway.mvc.exception.GatewayException;
import com.glpo.gateway.oc.validation.genericholder.Condition;
import com.glpo.gateway.oc.validation.genericholder.Operator;
import com.glpo.gateway.oc.validation.genericholder.ValidationItem;
import com.glpo.gateway.oc.validation.genericholder.ItemKind;

public class GenericParameterValidator implements ParameterValidator {
    private List<ValidationItem> validationItems;

    public String validate(String formName, String attrName, ValidationHelper helper) {
	String validatedAttrValue = helper.getAttrValueForForm(formName, attrName);
	ValidationItem isOtherwiseVI = null;
	for (ValidationItem item : validationItems) {
	    //log.debug("[GPV] Processing validation item isRequired = " + item.isRequired() + ", isOptional = " + item.isOptional() + ", isProhibited = " + item.isProhibited());
	    if (item.isOtherwise()) {
		//log.debug("[GPV] isOtherwise marker found");
		isOtherwiseVI = item;
	    } else {
		Collection<Condition> conditions = item.getAttrValidators();
		//log.debug("[GPV] Validation item contains " + (conditions == null ? 0 : conditions.size()) + " conditions");
		if (conditions != null && conditions.size() > 0) {
		    boolean validationPassed = true;
		    for (Condition condition : conditions) {
			//log.debug("[GPV] Begin validation of condition for attribute id = " + condition.getAttributeId());
			if (!condition.isAllKind() && !condition.isOneKind()) {			    
			    String value = helper.getAttrValueForForm(condition.getAsrForm(), condition.getAttrName());			    
			    validationPassed = checkByOperator(value, validatedAttrValue, validationPassed, condition, helper);
			} else {
//			    Collection<String> values = helper.getValues(condition.getAttrName());
//			    if (condition.isAllKind()) {
//				String requiredValue = null;
//				if (!values.isEmpty()) {
//				    requiredValue = helper.subvalue(helper.getNormalizedValue(values.iterator().next(),
//						    condition.isObjectName()), condition.getStartPosition(),
//					    condition.getEndPosition());
//				}
//				for (AttributeValue value : values) {
//				    String valueString = helper.subvalue(helper.getNormalizedValue(value, condition.isObjectName()),
//					    condition.getStartPosition(), condition.getEndPosition());
//				    validationPassed &= (requiredValue == null && valueString == null)
//					    || (requiredValue != null && requiredValue.equals(valueString));
//				    if (!validationPassed) {
//					break;
//				    }
//				}
//				AttributeValue value = null;
//				if (!values.isEmpty()) {
//				    value = values.iterator().next();
//				}
//				validationPassed = checkByOperator(, validationPassed, condition, value);
//			    } else if (condition.isOneKind()) {
//				for (AttributeValue value : values) {
//				    validationPassed = checkByOperator(, true, condition, value);
//				    if (validationPassed) {
//					break;
//				    }
//				}
//			    }
			}

		    }
		    if (validationPassed) {
			//log.debug("[GPV] Validation for this Validation item is passed");
			return generateErrorMessageIfRequired(validatedAttrValue, item);
		    } else {
			//log.debug("[GPV] Validation for this Validation item is failed");
		    }
		}
	    }
	}
	if (isOtherwiseVI != null) {
	   // log.debug("[GPV] All validation items failed, switching to isOtherwise");
	    return generateErrorMessageIfRequired(validatedAttrValue, isOtherwiseVI);
	}
	return null;
    }

    private boolean checkByOperator(String attrValue, String validatedAttrValue, boolean validationPassed, Condition condition, ValidationHelper helper) {
	Operator operator = condition.getOperator();
	if (operator.equals(EMPTY)) {
	    validationPassed &= checkEmptyOrNotEmpty(attrValue, condition, true);
	} else if (operator.equals(NOT_EMPTY)) {
	    validationPassed &= checkEmptyOrNotEmpty(attrValue, condition, false);
	} else if (operator.equals(IN)) {
	    validationPassed &= checkInOrNotIn(attrValue, condition, true);
	} else if (operator.equals(NOT_IN)) {
	    validationPassed &= checkInOrNotIn(attrValue, condition, false);
	} else if (operator.equals(EQUALS)) {
	    validationPassed &= checkEqualsOrNotEquals(attrValue, validatedAttrValue,condition, true, helper);
	} else if (operator.equals(NOT_EQUALS)) {
	    validationPassed &= checkEqualsOrNotEquals(attrValue, validatedAttrValue, condition, false, helper);
	} else if (operator.equals(EQ) || operator.equals(LT)
		|| operator.equals(LE) || operator.equals(GT)
		|| operator.equals(GE)) {
	    validationPassed &= checkMathOperation(attrValue, condition, operator);
	}
	return validationPassed;
    }

    private boolean checkEmptyOrNotEmpty(String value, Condition condition, boolean checkEmpty) {
	if (checkEmpty) {
	    if (value != null && !value.isEmpty()) {
		//log.debug("[GPV] Attribute should be empty but is not empty");
		return false;
	    } else {
		//log.debug("[GPV] Attribute should be empty and is empty");
		return true;
	    }
	} else {
	    if (value == null || value.isEmpty()) {
		//log.debug("[GPV] Attribute should be not empty but is empty");
		return false;
	    } else {
		//log.debug("[GPV] Attribute should not be empty and is not empty");
		return true;
	    }

	}
    }


    private boolean checkInOrNotIn(String value, Condition condition, boolean checkIn) {
		
	if (checkIn) {
	    if (!condition.getValues().contains(value)) {
		//log.debug("[GPV] Attribute should be in listed values but is not");
		return false;
	    } else {
		//log.debug("[GPV] Attribute should be in listed values and it is");
		return true;
	    }
	} else {
	    if (condition.getValues().contains(value)) {
		//log.debug("[GPV] Attribute should not be in listed values but it is");
		return false;
	    } else {
		//log.debug("[GPV] Attribute should not be in listed values and is not");
		return true;
	    }
	}

    }

    private boolean checkEqualsOrNotEquals(String firstValue, String validatedAttrValue, Condition condition, boolean checkEquals, ValidationHelper helper) {
	String secondValue;
	if (condition.getValues() != null && !condition.getValues().isEmpty()) {
	    secondValue = helper.getAttrValueForForm(condition.getAsrForm(), condition.getValues().iterator().next());
	} else {
	    secondValue = validatedAttrValue;
	}	

	if (checkEquals) {
	    if (firstValue != null && !firstValue.equals(secondValue) || secondValue != null
		    && !secondValue.equals(firstValue)) {
		//log.debug("[GPV] Attributes should be equals but are not");
		return false;
	    } else {
		//log.debug("[GPV] Attributes should be equals and they are");
		return true;
	    }
	} else {
	    if (firstValue == null && secondValue == null || firstValue != null && firstValue.equals(secondValue)) {
		//log.debug("[GPV] Attributes should not be equals but are not");
		return false;
	    } else {
		//log.debug("[GPV] Attributes should not be equals and they are");
		return true;
	    }
	}
    }

    private boolean checkMathOperation(String value, Condition condition, Operator operation) {
	long firstValue;
	long secondValue;
	
	try {
	    firstValue = Long.parseLong(value);
	} catch (NumberFormatException ex) {
	    return false;
	}
	String secondValueString = null;
	if (condition.getValues() != null && !condition.getValues().isEmpty()) {
	    secondValueString = condition.getValues().iterator().next();
	}
	try {
	    secondValue = Long.parseLong(secondValueString);
	} catch (NumberFormatException ex) {
	    return false;
	}

	if (operation.equals(Operator.EQ) && firstValue == secondValue || operation.equals(Operator.LT)
		&& firstValue < secondValue || operation.equals(Operator.LE) && firstValue <= secondValue
		|| operation.equals(Operator.GT) && firstValue > secondValue || operation.equals(Operator.GE)
		&& firstValue >= secondValue) {
	    return true;
	}
	return false;
    }

    private boolean attrIsEmpty(String attrValue) {
	return (attrValue == null || attrValue.isEmpty());
    }

    private boolean attrIsNotEmpty(String attrValue) {
	return (attrValue != null && !attrValue.isEmpty());
    }

    private boolean attrNotInAcceptableValues(ValidationItem item, String attrValue) {
	return (!item.getAcceptableValues().isEmpty() && !item.getAcceptableValues().contains(attrValue));
    }

    private boolean attrInAcceptableValues(ValidationItem item, String attrValue) {
	return (!item.getAcceptableValues().isEmpty() && item.getAcceptableValues().contains(attrValue));
    }

    private String generateErrorMessageIfRequired(String attrValue, ValidationItem item) {	
	    if ((item.isRequired() && (attrIsEmpty(attrValue) || attrNotInAcceptableValues(item, attrValue)))
		    || item.isProhibited() && (attrIsNotEmpty(attrValue) || attrInAcceptableValues(item, attrValue))) {
		//log.debug("[GPV] Error message is generated");
		return item.getErrorMessage();
	    }
	    //log.debug("[GPV] Error message is not generated");	
	return null;
    }

    public Collection<String> loadConfiguration(List<Element> list, String asrForm) {
	List<String> validateAttributes = new ArrayList<String>();
	validationItems = new ArrayList<ValidationItem>();
		
	for (Element validationItemElement : list) {
	    if (!validationItemElement.getName().equalsIgnoreCase(TARGET_ATTRIBUTES_TAG)
		    && !validationItemElement.getName().equalsIgnoreCase(ADDITIONAL_PARAMS_TAG)) {
		//log.debug("[GPV] Got validation item \"" + validationItemElement.getName() + "\"");

		List<String> acceptableValues = processAcceptableValuesTag(validationItemElement);

		List<Element> conditionElements = validationItemElement.getChildren(ATTR_TAG);
		//log.debug("[GPV] Got " + conditionElements.size() + " conditions");
		List<Condition> conditions = new ArrayList<Condition>(conditionElements.size());
		for (Element condition : conditionElements) {
		    //BigInteger attributeId = new BigInteger(condition.getAttributeValue(ID_TAG_ATTR));
		    String attrName = condition.getAttributeValue(ID_TAG_ATTR);
		    validateAttributes.add(attrName);
		    //log.debug("[GPV] Parsing condition \"" + condition.getAttributeValue(OPERATOR_TAG_ATTR) + "\" attribute=\"" + attributeId + "\"");
		    Operator operator = Operator.getOperator(condition.getAttributeValue(OPERATOR_TAG_ATTR));
		    Integer startPosition = getStartPosition(condition);
		    Integer endPosition = getEndPosition(condition);
		   // boolean isObjectName = isObjectName(condition);
		    boolean isAllKind = isAllKind(condition);
		    boolean isOneKind = isOneKind(condition);
		    //log.debug("[GPV] condition operation = \"" + operator.value() + "\"");
		   
		    String attrForm = condition.getAttributeValue(FORM_TAG);		   
		    String form = (attrForm != null && !attrForm.isEmpty()) ? attrForm : asrForm;
		    
		    List<String> stringValues = loadValues(condition, operator, validateAttributes);

		    conditions.add(new Condition(operator, attrName, form, startPosition, endPosition,
			    isAllKind, isOneKind, stringValues));		    
		}
		boolean isOtherwise = validationItemElement.getChild(OTHERWISE_TAG) != null;
		//log.debug("[GPV] \"Otherwise\" tag was " + (isOtherwise ? "" : "not ") + "found");
		String errorMessage = validationItemElement.getAttributeValue(ERROR_MESSAGE_TAG_ATTR);
		//log.debug("[GPV] Error message was " + ((errorMessage == null || errorMessage.isEmpty()) ? "not " : "") + "set");
		if (errorMessage != null && !errorMessage.isEmpty()) {
		    //log.debug("[GPV] Error message = \"" + errorMessage + "\"");
		}
		validationItems.add(new ValidationItem(ItemKind.getItemKind(validationItemElement.getName()),
			conditions, isOtherwise, errorMessage, acceptableValues));
	    } else if (validationItemElement.getName().equalsIgnoreCase(TARGET_ATTRIBUTES_TAG)) {
		List<Element> ids = validationItemElement.getChildren("id");
		for (Element id : ids) {
		    validateAttributes.add(id.getValue());
		}
	    }
	}
	//return checkAttributesExist(validateAttributes);
	return validateAttributes;
    }

    private List<String> processAcceptableValuesTag(Element validationItemElement) {
	List<Element> acceptableValuesTags = validationItemElement.getChildren("acceptable_values");

	//log.debug("[GPV] acceptable_values tag was " + (acceptableValuesTags != null && acceptableValuesTags.size() > 0 ? "" : "not ") + "found");
	if (acceptableValuesTags == null || acceptableValuesTags.size() == 0) {
	    return Collections.EMPTY_LIST;
	}
	Element acceptableValues = acceptableValuesTags.iterator().next();

	List<String> valuesList = loadValues(acceptableValues, Operator.IN, new ArrayList<String>());

	return valuesList;
    }

    private List<String> loadValues(Element condition, Operator operator, List<String> validateAttributes) {
	List<String> stringValues = null;
	//log.debug("[GPV] Values for condition are " + (operator.areValuesAcceptable() ? "" : "not ") + "required");
	if (operator.areValuesAcceptable()) {
	    //log.debug("[GPV] Values are:");
	    List<Element> values = condition.getChildren(VALUE_TAG);
	    if (operator.isOnlyOneAcceptable() && values.size() > 1) {
		throw new GatewayException("Only one value is acceptable for operator \"" + operator.value() + "\"");
	    }
	    if (isMathOperator(operator) && values.size() == 0) {
		throw new GatewayException("One value is required for mathematical operations");
	    }
	    stringValues = new ArrayList<String>(values.size());
	    for (Element value : values) {
//		String valueString = value.getValue();
//		if (operator.equals(Operator.EQUALS) || operator.equals(Operator.NOT_EQUALS)) {
//		    validateValueIsAttrId(valueString, validateAttributes);
//		}
//		if (isMathOperator(operator)) {
//		    validateValueIsInteger(valueString);
//		}
		stringValues.add(value.getValue());
		//log.debug("[GPV] " + value.getValue());
	    }
	}
	return stringValues;
    }

    private boolean isMathOperator(Operator operator) {
	return (operator.equals(EQ) || operator.equals(LT) || operator.equals(GT) || operator.equals(LE));
    }

    private Integer getStartPosition(Element condition) {
	Integer startPosition = null;
	String startPositionString = condition.getAttributeValue("start_pos");
	if (startPositionString != null && !startPositionString.isEmpty()) {
	    startPosition = new Integer(startPositionString);
	}
	return startPosition;
    }

    private Integer getEndPosition(Element condition) {
	Integer endPosition = null;
	String endPositionString = condition.getAttributeValue("end_pos");
	if (endPositionString != null && !endPositionString.isEmpty()) {
	    endPosition = new Integer(endPositionString);
	}
	return endPosition;
    }
    
    private boolean isAllKind(Element condition) {
   	String kind = condition.getAttributeValue("kind");
   	return "all".equals(kind);
    }

    private boolean isOneKind(Element condition) {
	String kind = condition.getAttributeValue("kind");
   	return "one".equals(kind);
    }
    

//    private boolean isObjectName(Element condition) {
//	String kind = condition.getAttributeValue("kind");
//	if ("name".equals(kind)) {
//	    BigInteger attrId = new BigInteger(condition.getAttributeValue(ID_TAG_ATTR));
//	    JDBCTemplates jdbc = NCCoreInternals.jdbcInstance();
//	    BigInteger attrType = jdbc.executeSelect(OCSQLConstants.GET_ATTR_TYPE, new Object[][] { { JDBCType.NUMBER,
//		    attrId } }, new ResultSetHandler<BigInteger>() {
//
//		@Override
//		public BigInteger onResultSet(ResultSet rs) throws SQLException {
//		    BigInteger attrType = BigInteger.ZERO;
//		    if (rs.next()) {
//			attrType = rs.getBigDecimal("type").toBigInteger();
//		    }
//		    return attrType;
//		}
//	    });
//	    if (REFERENCE.equals(AttrType.getAttrType(attrType.intValue()))) {
//		return true;
//	    } else {
//		throw new RuntimeException("Can't get object name because attribute id = " + attrId
//			+ " is not reference");
//	    }
//	}
//	return false;
//    }
   

//    private Collection<String> checkAttributesExist(List<String> validateAttributes) {
//	
//	List<String> existAttributes = null;
//	if (validateAttributes.size() != existAttributes.size()) {
//	    validateAttributes.removeAll(existAttributes);
//	    String error = "Following attributes were not found on server:";
//	    for (String attrName : validateAttributes) {
//		error += "\n" + attrName;
//	    }
//	    //log.debug("[GPV] " + error.replaceFirst("\n", " ").replaceAll("\n", ", "));
//	    throw new GatewayException(error);
//	}
//	return validateAttributes;
//    }
    
//  private void validateValueIsInteger(String valueString) {
//	try {
//	    new Integer(valueString);
//	} catch (NumberFormatException e) {
//	    //log.debug("[GPV] For mathematical operations only numbers are accepted");
//	    throw new RuntimeException("Number should be specified as value", e);
//	}
//  }

//  private void validateValueIsAttrId(String valueString, List<BigInteger> validateAttributes) {
//	try {
//	    validateAttributes.add(new BigInteger(valueString));
//	} catch (NumberFormatException e) {
//	    //log.debug("[GPV] Attribute id is expected as value");
//	    throw new RuntimeException("Attribute id should be specified in configuration", e);
//	}
//  }
    
//  private boolean checkMultipleEmptyOrNotEmpty(String value, Condition condition,  boolean checkEmpty) {
//	Collection<String> showValues = null;
//	if (value != null) {
//	    showValues = helper.subvalues(value.readMultipleShowValues(), condition.getStartPosition(),
//		    condition.getEndPosition());
//	}
//	boolean isNotEmpty = showValues != null && !showValues.isEmpty();
//	boolean hasNonEmpty = false;
//	if (isNotEmpty) {
//	    for (String showValue : showValues) {
//		if (showValue != null && !showValue.isEmpty()) {
//		    hasNonEmpty = true;
//		}
//	    }
//	}
//	if (checkEmpty) {
//	    if (isNotEmpty && hasNonEmpty) {
//		//log.debug("[GPV] All attribute values should be empty but at least one is not empty");
//		return false;
//	    } else {
//		//log.debug("[GPV] Attribute should be empty and is empty");
//		return true;
//	    }
//	} else {
//	    if (isNotEmpty && hasNonEmpty) {
//		//log.debug("[GPV] Attribute should not be empty and at least one value is not empty");
//		return true;
//	    } else {
//		//log.debug("[GPV] Attribute should be not empty but is empty");
//		return false;
//	    }
//	}
//  }
    
//  private boolean checkMultipleInOrNotIn(String value, Condition condition, boolean checkIn) {
//	Collection<String> values = null;
//	if (value != null) {
//	    values = helper.subvalues(value.getMultipleValues().values(), condition.getStartPosition(),
//		    condition.getEndPosition());
//	}
//	if (checkIn) {
//	    for (String stringValue : values) {
//		//log.debug("[GPV] Checking value: " + stringValue);
//		if (condition.getValues().contains(stringValue)) {
//		    //log.debug("[GPV] Attribute should be in listed values and it is");
//		    return true;
//		} else {
//		    //log.debug("[GPV] Attribute should be in listed values but is not");
//		}
//	    }
//	    return false;
//	} else {
//	    for (String stringValue : values) {
//		//log.debug("[GPV] Checking value: " + stringValue);
//		if (condition.getValues().contains(stringValue)) {
//		   // log.debug("[GPV] Attribute should not be in listed values but it is");
//		    return false;
//		} else {
//		    //log.debug("[GPV] Attribute should not be in listed values and is not");
//		}
//	    }
//	    return true;
//	}
//  }


}

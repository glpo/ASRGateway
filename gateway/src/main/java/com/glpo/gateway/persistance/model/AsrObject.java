package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;


import java.util.List;


/**
 * The persistent class for the ASR_OBJECT database table.
 * 
 */
@Entity
@Table(name="ASR_OBJECT")
public class AsrObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ASR_OBJECT_ASR_OBJECT_ID_SEQ", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ASR_OBJECT_ASR_OBJECT_ID_SEQ", sequenceName = "ASR_OBJECT_ASR_OBJECT_ID_SEQ", allocationSize = 1)
	@Column(name="ASR_OBJECT_ID")
	private long asrObjectId;

	private String name;

	@Column(name="\"VALUE\"")
	private String value;

	//bi-directional many-to-one association to AttributeValue
	@OneToMany(mappedBy="asrObject")
	private List<AttributeValue> attributeValues;

	public AsrObject() {
	}

	public long getAsrObjectId() {
		return this.asrObjectId;
	}

	public void setAsrObjectId(long asrObjectId) {
		this.asrObjectId = asrObjectId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<AttributeValue> getAttributeValues() {
		return this.attributeValues;
	}

	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public AttributeValue addAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().add(attributeValue);
		attributeValue.setAsrObject(this);

		return attributeValue;
	}

	public AttributeValue removeAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().remove(attributeValue);
		attributeValue.setAsrObject(null);

		return attributeValue;
	}

}
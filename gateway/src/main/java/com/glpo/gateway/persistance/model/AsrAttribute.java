package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the ASR_ATTRIBUTE database table.
 * 
 */
@Entity
@Table(name="ASR_ATTRIBUTE")
public class AsrAttribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ASR_ATTRIBUTE_ASR_ATTR_ID_SEQ", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ASR_ATTRIBUTE_ASR_ATTR_ID_SEQ", sequenceName = "ASR_ATTRIBUTE_ASR_ATTR_ID_SEQ", allocationSize = 1)
	@Column(name="ASR_ATTR_ID")
	private long asrAttrId;

	private String name;

	//bi-directional many-to-one association to AsrFormType
	@ManyToOne
	@JoinColumn(name="ASR_FORM_TYPE_ID")
	private AsrFormType asrFormType;

	//bi-directional many-to-one association to AttributeValue
	@OneToMany(mappedBy="asrAttribute")
	private List<AttributeValue> attributeValues;

	public AsrAttribute() {
	}

	public long getAsrAttrId() {
		return this.asrAttrId;
	}

	public void setAsrAttrId(long asrAttrId) {
		this.asrAttrId = asrAttrId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AsrFormType getAsrFormType() {
		return this.asrFormType;
	}

	public void setAsrFormType(AsrFormType asrFormType) {
		this.asrFormType = asrFormType;
	}

	public List<AttributeValue> getAttributeValues() {
		return this.attributeValues;
	}

	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public AttributeValue addAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().add(attributeValue);
		attributeValue.setAsrAttribute(this);

		return attributeValue;
	}

	public AttributeValue removeAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().remove(attributeValue);
		attributeValue.setAsrAttribute(null);

		return attributeValue;
	}

}
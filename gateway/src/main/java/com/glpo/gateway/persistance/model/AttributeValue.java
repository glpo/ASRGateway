package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the ATTRIBUTE_VALUE database table.
 * 
 */
@Entity
@Table(name="ATTRIBUTE_VALUE")
public class AttributeValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ATTRIBUTE_VALUE_ATTR_VALUE_ID", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ATTRIBUTE_VALUE_ATTR_VALUE_ID", sequenceName = "ATTRIBUTE_VALUE_ATTR_VALUE_ID", allocationSize = 1)
	@Column(name="ATTR_VALUE_ID")
	private long attrValueId;

	private String additioanl;

	@Column(name="\"VALUE\"")
	private String value;

	//bi-directional many-to-one association to AsrAttribute
	@ManyToOne
	@JoinColumn(name="ASR_ATTR_ID")
	private AsrAttribute asrAttribute;

	//bi-directional many-to-one association to AsrForm
	@ManyToOne
	@JoinColumn(name="ASR_FORM_ID")
	private AsrForm asrForm;

	//bi-directional many-to-one association to AsrObject
	@ManyToOne
	@JoinColumn(name="ASR_OBJECT_ID")
	private AsrObject asrObject;

	public AttributeValue() {
	}

	public long getAttrValueId() {
		return this.attrValueId;
	}

	public void setAttrValueId(long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getAdditioanl() {
		return this.additioanl;
	}

	public void setAdditioanl(String additioanl) {
		this.additioanl = additioanl;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AsrAttribute getAsrAttribute() {
		return this.asrAttribute;
	}

	public void setAsrAttribute(AsrAttribute asrAttribute) {
		this.asrAttribute = asrAttribute;
	}

	public AsrForm getAsrForm() {
		return this.asrForm;
	}

	public void setAsrForm(AsrForm asrForm) {
		this.asrForm = asrForm;
	}

	public AsrObject getAsrObject() {
		return this.asrObject;
	}

	public void setAsrObject(AsrObject asrObject) {
		this.asrObject = asrObject;
	}

}
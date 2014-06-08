package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;


import java.util.List;


/**
 * The persistent class for the ASR_FORM_TYPE database table.
 * 
 */
@Entity
@Table(name="ASR_FORM_TYPE")
public class AsrFormType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ASR_FORM_TYPE_ASR_FORM_TYPE_ID", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ASR_FORM_TYPE_ASR_FORM_TYPE_ID", sequenceName = "ASR_FORM_TYPE_ASR_FORM_TYPE_ID", allocationSize = 1)
	@Column(name="ASR_FORM_TYPE_ID")
	private long asrFormTypeId;

	@Column(name="FORM_TYPE")
	private String formType;

	//bi-directional many-to-one association to AsrAttribute
	@OneToMany(mappedBy="asrFormType")
	private List<AsrAttribute> asrAttributes;

	//bi-directional many-to-one association to AsrForm
	@OneToMany(mappedBy="asrFormType")
	private List<AsrForm> asrForms;

	public AsrFormType() {
	}

	public long getAsrFormTypeId() {
		return this.asrFormTypeId;
	}

	public void setAsrFormTypeId(long asrFormTypeId) {
		this.asrFormTypeId = asrFormTypeId;
	}

	public String getFormType() {
		return this.formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public List<AsrAttribute> getAsrAttributes() {
		return this.asrAttributes;
	}

	public void setAsrAttributes(List<AsrAttribute> asrAttributes) {
		this.asrAttributes = asrAttributes;
	}

	public AsrAttribute addAsrAttribute(AsrAttribute asrAttribute) {
		getAsrAttributes().add(asrAttribute);
		asrAttribute.setAsrFormType(this);

		return asrAttribute;
	}

	public AsrAttribute removeAsrAttribute(AsrAttribute asrAttribute) {
		getAsrAttributes().remove(asrAttribute);
		asrAttribute.setAsrFormType(null);

		return asrAttribute;
	}

	public List<AsrForm> getAsrForms() {
		return this.asrForms;
	}

	public void setAsrForms(List<AsrForm> asrForms) {
		this.asrForms = asrForms;
	}

	public AsrForm addAsrForm(AsrForm asrForm) {
		getAsrForms().add(asrForm);
		asrForm.setAsrFormType(this);

		return asrForm;
	}

	public AsrForm removeAsrForm(AsrForm asrForm) {
		getAsrForms().remove(asrForm);
		asrForm.setAsrFormType(null);

		return asrForm;
	}

}
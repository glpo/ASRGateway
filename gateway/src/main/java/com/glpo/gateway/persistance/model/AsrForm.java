package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ASR_FORM database table.
 * 
 */
@Entity
@Table(name = "ASR_FORM")
public class AsrForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ASR_FORM_ASR_FORM_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ASR_FORM_ASR_FORM_ID_SEQ", sequenceName = "ASR_FORM_ASR_FORM_ID_SEQ", allocationSize = 1)
    @Column(name = "ASR_FORM_ID")
    private long asrFormId;

    @Column(name = "MAIN_FORM")
    private long mainForm;
    
    @Column(name = "STATUS")
    private String status;
   
    @Column(name = "SUBMITION_DATE")
    private Date submitionDate;

    // bi-directional many-to-one association to AsrFormType
    @ManyToOne
    @JoinColumn(name = "ASR_FORM_TYPE_ID")
    private AsrFormType asrFormType;

    // bi-directional many-to-one association to Customer
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    // bi-directional many-to-one association to AttributeValue
    @OneToMany(mappedBy = "asrForm")
    private List<AttributeValue> attributeValues;

    public AsrForm() {
	attributeValues = new ArrayList<AttributeValue>();
    }

    public long getAsrFormId() {
	return this.asrFormId;
    }

    public void setAsrFormId(long asrFormId) {
	this.asrFormId = asrFormId;
    }

    public long getMainForm() {
	return this.mainForm;
    }

    public void setMainForm(long mainForm) {
	this.mainForm = mainForm;
    }

    public AsrFormType getAsrFormType() {
	return this.asrFormType;
    }

    public void setAsrFormType(AsrFormType asrFormType) {
	this.asrFormType = asrFormType;
    }

    public Customer getCustomer() {
	return this.customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public List<AttributeValue> getAttributeValues() {
	return this.attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
	this.attributeValues = attributeValues;
    }

    public Date getSubmitionDate() {
	return submitionDate;
    }

    public void setSubmitionDate(Date submitionDate) {
	this.submitionDate = submitionDate;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public AttributeValue addAttributeValue(AttributeValue attributeValue) {
	getAttributeValues().add(attributeValue);
	attributeValue.setAsrForm(this);

	return attributeValue;
    }

    public AttributeValue removeAttributeValue(AttributeValue attributeValue) {
	getAttributeValues().remove(attributeValue);
	attributeValue.setAsrForm(null);

	return attributeValue;
    }

}
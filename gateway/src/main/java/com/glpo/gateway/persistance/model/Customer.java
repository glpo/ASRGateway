package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the CUSTOMER database table.
 * 
 */
@Entity
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CUSTOMER_CUSTOMER_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CUSTOMER_CUSTOMER_ID_SEQ", sequenceName = "CUSTOMER_CUSTOMER_ID_SEQ", allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    private long customerId;

    private String city;

    private String email;

    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "\"STATE\"")
    private String state;

    private String street;

    @Column(name = "PASSWD")
    private String passwd;
    
    @Column(name = "IS_ACTIVE")
    private int isActive;    

    // bi-directional many-to-one association to AsrForm
    @OneToMany(mappedBy = "customer")
    private List<AsrForm> asrForms;

    public Customer() {
    }

    public long getCustomerId() {
	return this.customerId;
    }

    public void setCustomerId(long customerId) {
	this.customerId = customerId;
    }

    public String getCity() {
	return this.city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPhoneNumber() {
	return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getPasswd() {
	return passwd;
    }

    public void setPasswd(String passwd) {
	this.passwd = passwd;
    }

    public String getState() {
	return this.state;
    }
    
    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getStreet() {
	return this.street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public List<AsrForm> getAsrForms() {
	return this.asrForms;
    }

    public void setAsrForms(List<AsrForm> asrForms) {
	this.asrForms = asrForms;
    }

    public AsrForm addAsrForm(AsrForm asrForm) {
	getAsrForms().add(asrForm);
	asrForm.setCustomer(this);

	return asrForm;
    }

    public AsrForm removeAsrForm(AsrForm asrForm) {
	getAsrForms().remove(asrForm);
	asrForm.setCustomer(null);

	return asrForm;
    }

}
package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USERS_USER_ID_SEQ", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "USERS_USER_ID_SEQ", sequenceName = "USERS_USER_ID_SEQ", allocationSize = 1)
	@Column(name="USER_ID")
	private long userId;

	private String login;

	private String passwd;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="GROUP_ID")
	private Group group;

	public User() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
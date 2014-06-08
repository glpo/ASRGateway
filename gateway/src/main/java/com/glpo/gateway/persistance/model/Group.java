package com.glpo.gateway.persistance.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the "GROUPS" database table.
 * 
 */
@Entity
@Table(name="\"GROUPS\"")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "GROUPS_GROUP_ID_SEQ", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "GROUPS_GROUP_ID_SEQ", sequenceName = "GROUPS_GROUP_ID_SEQ", allocationSize = 1)
	@Column(name="GROUP_ID")
	private long groupId;

	@Column(name="GROUP_NAME")
	private String groupName;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="group")
	private List<User> users;

	public Group() {
	}

	public long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setGroup(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setGroup(null);

		return user;
	}

}
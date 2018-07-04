package com.ucustakvimi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "flight_users")
public class FlightUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String password;
	private String passwordConfirm;

	private String email;
	private String name;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	public Long getId() {
		return id;
	}

	/*
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinTable(name = "mon_user_accounts", joinColumns = @JoinColumn(name =
	 * "user_id"), inverseJoinColumns = @JoinColumn(name = "account_id")) public
	 * Set<MonAccount> getAccounts() { return accounts; }
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}

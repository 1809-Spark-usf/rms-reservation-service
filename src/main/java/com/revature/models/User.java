package com.revature.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class with basic information about user for front-end remember me capabilities.
 * @author Clay
 *
 */
@Entity
@Table(name="slack_users")
public class User {
	
	@Id
	@Column(name="slack_id")
	private String id;
	private String name;
	@Column(name="full_name")
	private String email;
	@Column(name="token_expiration")
	private LocalDate expiration;
	@Column(name="login_token")
	private String token;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String id, String name, String email, LocalDate expiration, String token) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.expiration = expiration;
		this.token = token;
	}
	public LocalDate getExpiration() {
		return expiration;
	}
	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}
	
	

}

package com.revature.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class with basic information about user for front-end remember me capabilities.
 * @author 1811-Java-Nick 12/27/18
 *
 */
@Entity
@Table(name="slack_users")
public class User {
	
	/** The id. */
	@Id
	@Column(name="slack_id")
	private String id;
	
	/** The name. */
	private String name;
	
	/** The email. */
	@Column(name="full_name")
	private String email;
	
	/** The expiration. */
	@Column(name="token_expiration")
	private LocalDate expiration;
	
	/** The token. */
	@Column(name="login_token")
	private String token;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param name the name
	 * @param email the email
	 * @param expiration the expiration
	 * @param token the token
	 */
	public User(String id, String name, String email, LocalDate expiration, String token) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.expiration = expiration;
		this.token = token;
	}
	
	/**
	 * Gets the expiration.
	 *
	 * @return the expiration
	 */
	public LocalDate getExpiration() {
		return expiration;
	}
	
	/**
	 * Sets the expiration.
	 *
	 * @param expiration the new expiration
	 */
	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}
	
	

}

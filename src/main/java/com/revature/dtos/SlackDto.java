package com.revature.dtos;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.models.User;

/**
 * DTO for the Slack API.
 * To do: Configure Jackson so names with underscores can be changes to camel case.
 * @author 1811-Java-Nick 12/27/18
 */
public class SlackDto {
	
	public boolean ok;
	public String access_token;
	public String scope;
	public User user;
	public ObjectNode team;
	public String warning;
	public ObjectNode response_metadata;
	public String error;
	
	/**
	 * 
	 * @return error
	 */
	public String getError() {
		return error;
	}
	/**
	 * 
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * 
	 * @return ok
	 */
	public boolean isOk() {
		return ok;
	}
	/**
	 * 
	 * @param ok
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	/**
	 * 
	 * @return ok
	 */
	public String getAccess_token() {
		return access_token;
	}
	/**
	 * 
	 * @param access_token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	/**
	 * 
	 * @return scope
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * 
	 * @param scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	/**
	 * 
	 * @return user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 
	 * @return team
	 */
	public ObjectNode getTeam() {
		return team;
	}
	/**
	 * 
	 * @param team
	 */
	public void setTeam(ObjectNode team) {
		this.team = team;
	}
	/**
	 * 
	 * @return warning
	 */
	public String getWarning() {
		return warning;
	}
	/**
	 * 
	 * @param warning
	 */
	public void setWarning(String warning) {
		this.warning = warning;
	}
	/**
	 * 
	 * @return responese_metadata
	 */
	public ObjectNode getResponse_metadata() {
		return response_metadata;
	}
	/**
	 * 
	 * @param response_metadata
	 */
	public void setResponse_metadata(ObjectNode response_metadata) {
		this.response_metadata = response_metadata;
	}
	/**
	 * No arg constructor for the SlackDto class
	 */
	public SlackDto() {
		super();
	}
	

}

package com.revature.dtos;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.models.User;

/**
 * DTO for the Slack API.
 * To do: Configure Jackson so names with underscores can be changes to camel case.
 *
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
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ObjectNode getTeam() {
		return team;
	}
	public void setTeam(ObjectNode team) {
		this.team = team;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	public ObjectNode getResponse_metadata() {
		return response_metadata;
	}
	public void setResponse_metadata(ObjectNode response_metadata) {
		this.response_metadata = response_metadata;
	}
	public SlackDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}

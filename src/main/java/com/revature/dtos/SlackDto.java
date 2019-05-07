package com.revature.dtos;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.models.User;

/**
 * DTO for the Slack API.
 * To do: Configure Jackson so names with underscores can be changes to camel case.
 * @author 1811-Java-Nick 12/27/18
 */
public class SlackDto {
	
	private boolean ok;
	private String accessToken;
	private String scope;
	private User user;
	private ObjectNode team;
	private String warning;
	private ObjectNode responseMetadata;
	private String error;
	
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
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * 
	 * @param access_token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
	public ObjectNode getResponseMetadata() {
		return responseMetadata;
	}
	/**
	 * 
	 * @param response_metadata
	 */
	public void setResponseMetadata(ObjectNode responseMetadata) {
		this.responseMetadata = responseMetadata;
	}
	/**
	 * No arg constructor for the SlackDto class
	 */
	public SlackDto() {
		super();
	}
	public SlackDto(boolean ok, String accessToken, String scope, User user, ObjectNode team, String warning,
			ObjectNode responseMetadata, String error) {
		super();
		this.ok = ok;
		this.accessToken = accessToken;
		this.scope = scope;
		this.user = user;
		this.team = team;
		this.warning = warning;
		this.responseMetadata = responseMetadata;
		this.error = error;
	}
	@Override
	public String toString() {
		return "SlackDto [ok=" + ok + ", accessToken=" + accessToken + ", scope=" + scope + ", user=" + user + ", team="
				+ team + ", warning=" + warning + ", responseMetadata=" + responseMetadata + ", error=" + error + "]";
	}
	

}

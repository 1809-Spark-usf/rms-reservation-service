package com.revature.dtos;

/**
 * Data transfer object for the response from 
 * the Google Calendar API 
 * @author 1811-Java-Nick 12/27/18
 */
public class GoogleDto {
	
	private String accessToken;
	private String idToken;
	private String refreshToken;
	private String expiresIn;
	private String tokenType;
	
	/**
	 * To string of the GoogleDto class
	 */
	@Override
	public String toString() {
		return "GoogleDto [accessToken=" + accessToken + ", idToken=" + idToken + ", refreshToken=" + refreshToken
				+ ", expiresIn=" + expiresIn + ", tokenType=" + tokenType + "]";
	}
	/**
	 * 
	 * @return access_token
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * 
	 * @param access_token string to set a unique token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * 
	 * @return id_token
	 */
	public String getIdToken() {
		return idToken;
	}
	/**
	 * 
	 * @param id_token
	 */
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	
	/**
	 * Gets the refresh token.
	 *
	 * @return refresh_token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}
	/**
	 * 
	 * @param refresh_token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	/**
	 * 
	 * @return expires_in
	 */
	public String getExpiresIn() {
		return expiresIn;
	}
	/**
	 * 
	 * @param expires_in
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	/**
	 * 
	 * @return token_type
	 */
	public String getTokenType() {
		return tokenType;
	}
	/**
	 * 
	 * @param token_type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	/**
	 * no arg constructor for the GoogleDto class
	 */
	public GoogleDto() {
		super();
	}
	
}


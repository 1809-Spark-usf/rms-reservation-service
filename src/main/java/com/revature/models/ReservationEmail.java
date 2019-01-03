package com.revature.models;

import java.time.LocalDateTime;

/**
 * The ReservationEmail Class
 * Used to transfer information needed
 * by the RMSEmailService in an object
 * @author Austin D. 1811-Java-Nick 1/3/19 
 *
 */
public class ReservationEmail {
	/**The email of the user making the reservation*/
	private String email;
	/**The start time of the reservation*/
	private LocalDateTime startTime;
	/**The end time of the reservation*/
	private LocalDateTime endTime;
	/**The building name the reservation is taking place in*/
	private String buildingName;
	/**The resource name associated with the building*/
	private String resourceName;
	public ReservationEmail() {
		super();
	}
	@Override
	public String toString() {
		return "ReservationEmail [email=" + email + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", buildingName=" + buildingName + ", resourceName=" + resourceName + "]";
	}
	public ReservationEmail(String email, LocalDateTime startTime, LocalDateTime endTime, String buildingName,
			String resourceName) {
		super();
		this.email = email;
		this.startTime = startTime;
		this.endTime = endTime;
		this.buildingName = buildingName;
		this.resourceName = resourceName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}

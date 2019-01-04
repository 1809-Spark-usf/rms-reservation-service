package com.revature.dtos;

import java.time.LocalDateTime;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.revature.enumerations.Purpose;
import com.revature.models.Resource;

public class ReservationDto {

	/** The purpose. */
	@NotNull
	private Purpose purpose;
	
	/** The start time. */
	private LocalDateTime startTime;
	
	/** The end time. */
	private LocalDateTime endTime;

	/** The resource. */
	@Transient
	private Resource resource;

	public Purpose getPurpose() {
		return purpose;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/** The resource id. */
	private int resourceId;
	
	/** The user id. */
	private String userId;
	
	/** The cancelled. */
	private boolean cancelled;
	
	/** The approved. */
	private boolean approved;
}

package com.revature.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.revature.enumerations.Purpose;

// 
/**
 * The Class Reservation.
 * @author 1811-Java-Nick 12/27/18
 */
@Entity
@Table(name = "reservations")
public class Reservation {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

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

	/** The resource id. */
	private int resourceId;
	
	/** The user id. */
	private String userId;
	
	/** The cancelled. */
	private boolean cancelled;
	
	/** The approved. */
	private boolean approved;

	private int reminderTime;
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public int getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(int reminderTime) {
		this.reminderTime = reminderTime;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the purpose.
	 *
	 * @return the purpose
	 */
	public Purpose getPurpose() {
		return purpose;
	}

	/**
	 * Sets the purpose.
	 *
	 * @param purpose the new purpose
	 */
	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the resource.
	 *
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * Sets the resource.
	 *
	 * @param resource the new resource
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * Gets the resource id.
	 *
	 * @return the resource id
	 */
	public int getResourceId() {
		return resourceId;
	}

	/**
	 * Sets the resource id.
	 *
	 * @param resourceId the new resource id
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Checks if is cancelled.
	 *
	 * @return true, if is cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the cancelled.
	 *
	 * @param cancelled the new cancelled
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * Checks if is approved.
	 *
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Sets the approved.
	 *
	 * @param approved the new approved
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * Instantiates a new reservation.
	 */
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new reservation.
	 *
	 * @param id the id
	 * @param purpose the purpose
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param resource the resource
	 * @param resourceId the resource id
	 * @param userId the user id
	 * @param cancelled the cancelled
	 * @param approved the approved
	 */
	public Reservation(int id, @NotNull Purpose purpose, LocalDateTime startTime, LocalDateTime endTime,
			Resource resource, int resourceId, String userId, boolean cancelled, boolean approved, int reminderTime) {
		super();
		this.id = id;
		this.purpose = purpose;
		this.startTime = startTime;
		this.endTime = endTime;
		this.resource = resource;
		this.resourceId = resourceId;
		this.userId = userId;
		this.cancelled = cancelled;
		this.approved = approved;
		this.reminderTime = reminderTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + (cancelled ? 1231 : 1237);
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + id;
		result = prime * result + ((purpose == null) ? 0 : purpose.hashCode());
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		result = prime * result + resourceId;
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (approved != other.approved)
			return false;
		if (cancelled != other.cancelled)
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id != other.id)
			return false;
		if (purpose != other.purpose)
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (resourceId != other.resourceId)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", purpose=" + purpose + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", resource=" + resource + ", resourceId=" + resourceId + ", userId=" + userId + ", cancelled="
				+ cancelled + ", approved=" + approved + ", reminderTime=" + reminderTime + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	

}

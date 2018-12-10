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

@Entity
@Table(name="reservations")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	@NotNull
	private Purpose purpose;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	@Transient
	private Resource resource;
	
	private int resourceID;
	private int userId;
	private boolean cancelled;
	private boolean approved;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getResourceID() {
		return resourceID;
	}
	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
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
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reservation(int id, @NotNull Purpose purpose, LocalDateTime startTime, LocalDateTime endTime,
			Resource resource, int resourceID, int userId, boolean cancelled, boolean approved) {
		super();
		this.id = id;
		this.purpose = purpose;
		this.startTime = startTime;
		this.endTime = endTime;
		this.resource = resource;
		this.resourceID = resourceID;
		this.userId = userId;
		this.cancelled = cancelled;
		this.approved = approved;
	}
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
		result = prime * result + resourceID;
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + userId;
		return result;
	}
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
		if (resourceID != other.resourceID)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", purpose=" + purpose + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", resource=" + resource + ", resourceID=" + resourceID + ", userId=" + userId + ", cancelled="
				+ cancelled + ", approved=" + approved + "]";
	}
	
	

}

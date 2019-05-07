package com.revature.dtos;

import java.time.LocalDateTime;
import com.revature.enumerations.Purpose;
import com.revature.models.Resource;

public class ReservationDto {

		/** The id. */
		private int id;

		/** The purpose. */
		private Purpose purpose;
		
		/** The start time. */
		private LocalDateTime startTime;
		
		/** The end time. */
		private LocalDateTime endTime;

		/** The resource. */
		private Resource resource;

		/** The resource id. */
		private int resourceId;
		
		/** The user id. */
		private String userId;
		
		public ReservationDto() {
			super();
		}

		public ReservationDto(int id, Purpose purpose, LocalDateTime startTime, LocalDateTime endTime,
				Resource resource, int resourceId, String userId, boolean cancelled, boolean approved,
				int reminderTime) {
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

		@Override
		public String toString() {
			return "ReservationDto [id=" + id + ", purpose=" + purpose + ", startTime=" + startTime + ", endTime="
					+ endTime + ", resource=" + resource + ", resourceId=" + resourceId + ", userId=" + userId
					+ ", cancelled=" + cancelled + ", approved=" + approved + ", reminderTime=" + reminderTime + "]";
		}

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

		public int getReminderTime() {
			return reminderTime;
		}

		public void setReminderTime(int reminderTime) {
			this.reminderTime = reminderTime;
		}

		/** The cancelled. */
		private boolean cancelled;
		
		/** The approved. */
		private boolean approved;

		private int reminderTime;
}

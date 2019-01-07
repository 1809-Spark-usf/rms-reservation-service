package com.revature.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.revature.enumerations.Type;

/**
 * The Class Resource.
 * @author 1811-Java-Nick 12/27/18
 */
@Entity
@Table(name = "resources")
public class Resource {

	@Override
	public String toString() {
		return "Resource [id=" + id + ", type=" + type + ", campus=" + campus + ", building=" + building
				+ ", buildingId=" + buildingId + ", name=" + name + ", disabled=" + disabled + ", inactive=" + inactive
				+ ", retired=" + retired + ", availableStartDate=" + availableStartDate + ", reservableAfter="
				+ reservableAfter + ", reservableBefore=" + reservableBefore + ", availableDays=" + availableDays
				+ ", hasEthernet=" + hasEthernet + ", hasComputer=" + hasComputer + ", numberOfOutlets="
				+ numberOfOutlets + ", hasMicrophone=" + hasMicrophone + "]";
	}

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/** The type. */
	@NotNull(message = "Type is required.")
	private Type type;
	
	/** The campus. */
	@Transient
	private Campus campus;
	
	/** The building. */
	@ManyToOne
	@JoinColumn(name="building_id", nullable=false)
	private Building building;
	
	/** The building id. */
	@Transient
	private int buildingId;
	
	/** The name. */
	private String name;
	
	/** The disabled. */
	private boolean disabled;
	
	/** The inactive. */
	private boolean inactive;
	
	/** The retired. */
	private boolean retired;
	
	/** The available start date. */
	@Column(name="available_start_date")
	private LocalDateTime availableStartDate;
	
	/** The reservable after. */
	@Column(name="reservable_after")
	private LocalDateTime reservableAfter;
	
	/** The reservable before. */
	@Column(name = "reservable_before")
	private LocalDateTime reservableBefore;
	
	/** The available days. */
	@Column(name="available_days")
	@ElementCollection(targetClass=DayOfWeek.class)
	private List<DayOfWeek> availableDays;
	
	/** The has ethernet. */
	@Column(name="has_ethernet")
	private boolean hasEthernet;
	
	/** The has computer. */
	@Column(name = "has_computer")
	private boolean hasComputer;
	
	/** The number of outlets. */
	@Column(name = "number_of_outlets")
	private int numberOfOutlets;
	
	/** The has microphone. */
	@Column(name = "has_microphone")
	private boolean hasMicrophone;

	/**
	 * Gets the building.
	 *
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Sets the building.
	 *
	 * @param building the new building
	 */
	public void setBuilding(Building building) {
		this.building = building;
		this.buildingId = building.getId();
	}

	/**
	 * Gets the building id.
	 *
	 * @return the building id
	 */
	public int getBuildingId() {
		return buildingId;
	}

	/**
	 * Sets the building id.
	 *
	 * @param buildingId the new building id
	 */
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	/**
	 * Gets the available start date.
	 *
	 * @return the available start date
	 */
	public LocalDateTime getAvailableStartDate() {
		return availableStartDate;
	}

	/**
	 * Sets the available start date.
	 *
	 * @param availableStartDate the new available start date
	 */
	public void setAvailableStartDate(LocalDateTime availableStartDate) {
		this.availableStartDate = availableStartDate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Checks if is disabled.
	 *
	 * @return true, if is disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Checks if is inactive.
	 *
	 * @return true, if is inactive
	 */
	public boolean isInactive() {
		return inactive;
	}

	/**
	 * Sets the inactive.
	 *
	 * @param inactive the new inactive
	 */
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	/**
	 * Checks if is retired.
	 *
	 * @return true, if is retired
	 */
	public boolean isRetired() {
		return retired;
	}

	/**
	 * Sets the retired.
	 *
	 * @param retired the new retired
	 */
	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	/**
	 * Checks if is checks for ethernet.
	 *
	 * @return true, if is checks for ethernet
	 */
	public boolean isHasEthernet() {
		return hasEthernet;
	}

	/**
	 * Sets the checks for ethernet.
	 *
	 * @param hasEthernet the new checks for ethernet
	 */
	public void setHasEthernet(boolean hasEthernet) {
		this.hasEthernet = hasEthernet;
	}

	/**
	 * Checks if is checks for computer.
	 *
	 * @return true, if is checks for computer
	 */
	public boolean isHasComputer() {
		return hasComputer;
	}

	/**
	 * Sets the checks for computer.
	 *
	 * @param hasComputer the new checks for computer
	 */
	public void setHasComputer(boolean hasComputer) {
		this.hasComputer = hasComputer;
	}

	/**
	 * Gets the number of outlets.
	 *
	 * @return the number of outlets
	 */
	public int getNumberOfOutlets() {
		return numberOfOutlets;
	}

	/**
	 * Sets the number of outlets.
	 *
	 * @param numberOfOutlets the new number of outlets
	 */
	public void setNumberOfOutlets(int numberOfOutlets) {
		this.numberOfOutlets = numberOfOutlets;
	}

	/**
	 * Checks if is checks for microphone.
	 *
	 * @return true, if is checks for microphone
	 */
	public boolean isHasMicrophone() {
		return hasMicrophone;
	}

	/**
	 * Sets the checks for microphone.
	 *
	 * @param hasMicrophone the new checks for microphone
	 */
	public void setHasMicrophone(boolean hasMicrophone) {
		this.hasMicrophone = hasMicrophone;
	}

	/**
	 * Gets the reservable after.
	 *
	 * @return the reservable after
	 */
	public LocalDateTime getReservableAfter() {
		return reservableAfter;
	}

	/**
	 * Sets the reservable after.
	 *
	 * @param reservableAfter the new reservable after
	 */
	public void setReservableAfter(LocalDateTime reservableAfter) {
		this.reservableAfter = reservableAfter;
	}

	/**
	 * Gets the reservable before.
	 *
	 * @return the reservable before
	 */
	public LocalDateTime getReservableBefore() {
		return reservableBefore;
	}

	/**
	 * Sets the reservable before.
	 *
	 * @param reservableBefore the new reservable before
	 */
	public void setReservableBefore(LocalDateTime reservableBefore) {
		this.reservableBefore = reservableBefore;
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
	 * Gets the campus.
	 *
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}

	/**
	 * Sets the campus.
	 *
	 * @param campus the new campus
	 */
	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	/**
	 * Gets the available days.
	 *
	 * @return the available days
	 */
	public List<DayOfWeek> getAvailableDays() {
		return availableDays;
	}

	/**
	 * Sets the available days.
	 *
	 * @param availableDays the new available days
	 */
	public void setAvailableDays(List<DayOfWeek> availableDays) {
		this.availableDays = availableDays;
	}

}

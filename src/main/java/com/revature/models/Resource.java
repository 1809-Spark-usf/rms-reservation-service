package com.revature.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

enum Type {
	CUBICLE, OFFICE
}

enum Location {
	RESTON, USF
}

@Entity
@Table(name="resources")
public class Resource {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull(message="Type is required.")
	private Type type;
	@NotNull(message="Location is required.")
	private Location location;
	private String name;
	private boolean disabled;
	private boolean inactive;
	private boolean retired;
	@Column(name="useable_from")
	private LocalDateTime useableFrom;
	@Column(name="reservable_after")
	private LocalDateTime reservableAfter;
	@Column(name="reservable_before")
	private LocalDateTime reservableBefore;
	@Column(name="has_ethernet")
	private boolean hasEthernet;
	@Column(name="has_computer")
	private boolean hasComputer;
	@Column(name="number_of_outlets")
	private int numberOfOutlets;
	@Column(name="has_microphone")
	private boolean hasMicrophone;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public boolean isInactive() {
		return inactive;
	}
	
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
	public boolean isRetired() {
		return retired;
	}
	
	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	public boolean isHasEthernet() {
		return hasEthernet;
	}
	
	public void setHasEthernet(boolean hasEthernet) {
		this.hasEthernet = hasEthernet;
	}
	
	public boolean isHasComputer() {
		return hasComputer;
	}
	
	public void setHasComputer(boolean hasComputer) {
		this.hasComputer = hasComputer;
	}
	
	public int getNumberOfOutlets() {
		return numberOfOutlets;
	}
	
	public void setNumberOfOutlets(int numberOfOutlets) {
		this.numberOfOutlets = numberOfOutlets;
	}
	
	public boolean isHasMicrophone() {
		return hasMicrophone;
	}
	
	public void setHasMicrophone(boolean hasMicrophone) {
		this.hasMicrophone = hasMicrophone;
	}
	
	public LocalDateTime getReservableAfter() {
		return reservableAfter;
	}
	
	public void setReservableAfter(LocalDateTime reservableAfter) {
		this.reservableAfter = reservableAfter;
	}
	
	public LocalDateTime getReservableBefore() {
		return reservableBefore;
	}
	
	public void setReservableBefore(LocalDateTime reservableBefore) {
		this.reservableBefore = reservableBefore;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDateTime getUseableFrom() {
		return useableFrom;
	}
	
	public void setUseableFrom(LocalDateTime useableFrom) {
		this.useableFrom = useableFrom;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + (hasComputer ? 1231 : 1237);
		result = prime * result + (hasEthernet ? 1231 : 1237);
		result = prime * result + (hasMicrophone ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + (inactive ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + numberOfOutlets;
		result = prime * result + ((reservableAfter == null) ? 0 : reservableAfter.hashCode());
		result = prime * result + ((reservableBefore == null) ? 0 : reservableBefore.hashCode());
		result = prime * result + (retired ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((useableFrom == null) ? 0 : useableFrom.hashCode());
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
		Resource other = (Resource) obj;
		if (disabled != other.disabled)
			return false;
		if (hasComputer != other.hasComputer)
			return false;
		if (hasEthernet != other.hasEthernet)
			return false;
		if (hasMicrophone != other.hasMicrophone)
			return false;
		if (id != other.id)
			return false;
		if (inactive != other.inactive)
			return false;
		if (location != other.location)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfOutlets != other.numberOfOutlets)
			return false;
		if (reservableAfter == null) {
			if (other.reservableAfter != null)
				return false;
		} else if (!reservableAfter.equals(other.reservableAfter))
			return false;
		if (reservableBefore == null) {
			if (other.reservableBefore != null)
				return false;
		} else if (!reservableBefore.equals(other.reservableBefore))
			return false;
		if (retired != other.retired)
			return false;
		if (type != other.type)
			return false;
		if (useableFrom == null) {
			if (other.useableFrom != null)
				return false;
		} else if (!useableFrom.equals(other.useableFrom))
			return false;
		return true;
	}
	
	public Resource(int id, @NotNull(message = "Type is required.") Type type,
			@NotNull(message = "Location is required.") Location location, String name, boolean disabled,
			boolean inactive, boolean retired, LocalDateTime useableFrom, LocalDateTime reservableAfter,
			LocalDateTime reservableBefore, boolean hasEthernet, boolean hasComputer, int numberOfOutlets,
			boolean hasMicrophone) {
		super();
		this.id = id;
		this.type = type;
		this.location = location;
		this.name = name;
		this.disabled = disabled;
		this.inactive = inactive;
		this.retired = retired;
		this.useableFrom = useableFrom;
		this.reservableAfter = reservableAfter;
		this.reservableBefore = reservableBefore;
		this.hasEthernet = hasEthernet;
		this.hasComputer = hasComputer;
		this.numberOfOutlets = numberOfOutlets;
		this.hasMicrophone = hasMicrophone;
	}
	
	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Resource [id=" + id + ", type=" + type + ", location=" + location + ", name=" + name + ", disabled="
				+ disabled + ", inactive=" + inactive + ", retired=" + retired + ", useableFrom=" + useableFrom
				+ ", reservableAfter=" + reservableAfter + ", reservableBefore=" + reservableBefore + ", hasEthernet="
				+ hasEthernet + ", hasComputer=" + hasComputer + ", numberOfOutlets=" + numberOfOutlets
				+ ", hasMicrophone=" + hasMicrophone + "]";
	}
	
}
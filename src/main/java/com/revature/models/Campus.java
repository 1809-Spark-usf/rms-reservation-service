package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class Campus.
 *
 * @author 1811-Java-Nick 12/27/18
 */
@Entity
@Table(name="campuses")
public class Campus {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/** The name. */
	private String name;
	
	/** The buildings. */
	@OneToMany(mappedBy="campus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Building> buildings;

	/**
	 * Gets the id.
	 *
	 * @return id
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
	 * Gets the name.
	 *
	 * @return name
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
	 * Gets the buildings.
	 *
	 * @return buildings
	 */
	public List<Building> getBuildings() {
		return buildings;
	}

	/**
	 * Sets the buildings.
	 *
	 * @param buildings the new buildings
	 */
	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	/**
	 * This method overrides the hashCode() method for a manual implementation related to
	 * buildings, id, and name.
	 * @return result
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildings == null) ? 0 : buildings.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Overriden equals() method.
	 *
	 * @param obj the obj
	 * @return true or false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campus other = (Campus) obj;
		if (buildings == null) {
			if (other.buildings != null)
				return false;
		} else if (!buildings.equals(other.buildings))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Overridden toString() method.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Campus [id=" + id + ", name=" + name + ", buildings=" + buildings + "]";
	}

	/**
	 * Instantiates a new campus.
	 *
	 * @param id the id
	 * @param name the name
	 * @param buildings the buildings
	 */
	public Campus(int id, String name, List<Building> buildings) {
		super();
		this.id = id;
		this.name = name;
		this.buildings = buildings;
	}

	/**
	 * No argument constructor.
	 */
	public Campus() {
		super();
	}
}

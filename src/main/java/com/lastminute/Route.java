package com.lastminute;

import java.util.ArrayList;
import java.util.List;

//This information could be handled by using a database, with a DAO pattern.
//For the purpose of this exercise was not possible to use external java libraries (so mysql connector, for example, could not be used)

public class Route {

	private String origin;
	private String destination;
	private List<Flight> flights = new ArrayList<Flight>();

	public Route(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}


	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}


	/**
	 * Get the list of flights' information for this particular route
	 * @return
	 */
	public List<Flight> getFlights()
	{
		return this.flights;
	}

	/**
	 * Get the origin of the route
	 * @return
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Get the destination of the route
	 * @return
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Set the origin of the route
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Set the destination of the route
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Return all the information of this class
	 * @return
	 */
	public String toString() {
		return "Route [origin=" + origin + ", destination=" + destination + ", flights=" + flights + "]";
	}

	/**
	 * Hashcode for the class
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((flights == null) ? 0 : flights.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	/**
	 * Equals method for the class
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (flights == null) {
			if (other.flights != null)
				return false;
		} else if (!flights.equals(other.flights))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}


}

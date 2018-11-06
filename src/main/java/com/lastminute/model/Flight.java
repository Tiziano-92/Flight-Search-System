package com.lastminute.model;

import java.math.BigDecimal;

import com.lastminute.exception.LastMinuteException;

// This information could be handled by using a database, with a DAO pattern.
// For the purpose of this exercise was not possible to use external java libraries (so mysql connector, for example, could not be used)

/**
 * Class used to model the Flight entity
 * @author Tiziano
 *
 */
public class Flight {

	private String flightCode;
	private BigDecimal price;

	public Flight(String flightCode, BigDecimal price) throws LastMinuteException {
		if(flightCode == "")
		{
			throw new LastMinuteException("Flight code cannot be omitted");
		}
		if(price.compareTo(BigDecimal.ZERO) < 0)
		{
			throw new LastMinuteException("Price must be greather than zero");
		}
		this.flightCode = flightCode;
		this.price = price;
	}

	/**
	 * Get the code of the flight
	 * @return
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * Set the code of the flight
	 * @param flightCode
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	/**
	 * Get the price of the flight
	 * @return
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Set the price of the flight
	 * @param price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Hashcode method for the class
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightCode == null) ? 0 : flightCode.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	/**
	 * Equals method for the class
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (flightCode == null) {
			if (other.flightCode != null)
				return false;
		} else if (!flightCode.equals(other.flightCode))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	/**
	 * Print the flight's information
	 */
	@Override
	public String toString() {
		return flightCode + "," + price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}

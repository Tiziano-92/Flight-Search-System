package com.lastminute;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.lastminute.LastMinuteException;
import com.lastminute.NotFoundException;
import com.lastminute.Flight;
import com.lastminute.Route;

/**
 * The class which handles the flight research information
 * @author Tiziano
 *
 */
public class FlightSearchingSystem {

	private Route route;
	// It could be also created a Map<Flight, BigDecimal> in the case we wanted to save information regarding the flight also
	private Map<String, BigDecimal> ticketPriceFlights = new LinkedHashMap<String, BigDecimal>();

	public FlightSearchingSystem(Route route) throws LastMinuteException
	{
		this.route = route;
	}

	/**
	 * Make the research of the flights and ticket prices
	 * @param numDaysToDeparture
	 * @param basePrice
	 * @throws LastMinuteException
	 */
	public void searchFlights(int passengerNumber, Date departureDate) throws LastMinuteException
	{
		long daysPriorDeparture = this.getDaysPriorDeparture(departureDate);
		BigDecimal discountedPrice;

		if(this.route.getDestination().isEmpty() || this.route.getOrigin().isEmpty())
		{
			throw new LastMinuteException("A route and a destination must be provided for the research");
		}

		if(passengerNumber <= 0){
			throw new LastMinuteException("The number of passengers has to be above zero");
		}

		if(daysPriorDeparture < 0)
		{
			throw new LastMinuteException("The departure date is mandatory and it should be ahead the current date");
		}

		for (Flight flight : this.route.getFlights()) {
			discountedPrice = PriceCalculator.getDiscountedPrice(passengerNumber, daysPriorDeparture, flight.getPrice());
			this.ticketPriceFlights.put(flight.getFlightCode(), discountedPrice);
		}
	}

	/**
	 * Get the number of days prior to the departure of the flight
	 * @param departureDate
	 * @return
	 */
	public long getDaysPriorDeparture (Date departureDate)
	{
		Date currentDate = new Date();
		long diffInMillies = departureDate.getTime() - currentDate.getTime();
	    long daysPriorDeparture = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    return daysPriorDeparture;
	}

	/**
	 * Get the discounted routes
	 * @return
	 * @throws NotFoundException
	 */
	public Map<String, BigDecimal> getFlightResult() throws NotFoundException
	{
		if(this.ticketPriceFlights.isEmpty())
		{
			throw new NotFoundException("No flights available for the defined route");
		}
		return this.ticketPriceFlights;
	}

	/**
	 * Get the route you decided to search for
	 * @return
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Set the route you want to search flights for
	 * @param route
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * Get all the information about the research of flights
	 */
	public String toString() {
		String output = "";
		for (String key : ticketPriceFlights.keySet()) {
			output += key + ", " + ticketPriceFlights.get(key)+"\n";
		}
		return output;
	}
}
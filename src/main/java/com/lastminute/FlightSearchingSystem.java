package com.lastminute;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.lastminute.exception.LastMinuteException;
import com.lastminute.exception.NotFoundException;
import com.lastminute.model.Flight;
import com.lastminute.model.Route;

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
		long daysPriorDeparture = PriceCalculator.getDaysPriorDeparture(departureDate);
		BigDecimal discountedPrice;

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
	@Override
	public String toString() {
		String output = "";
		for (String key : ticketPriceFlights.keySet()) {
			output += key + ", " + ticketPriceFlights.get(key)+"\n";
		}
		return output;
	}
}
package com.lastminute;

import static com.lastminute.CsvFiles.readAllRecords;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lastminute.exception.LastMinuteException;
import com.lastminute.exception.NotFoundException;
import com.lastminute.model.Flight;
import com.lastminute.model.Route;
import com.lastminute.FlightSearchingSystem;

/**
 * Test class with Junit test for the searching flight system
 * @author Tiziano
 *
 */
public class TicketPriceRoutesTest {

	@Test
	public void getRoutesAmsToFra() throws LastMinuteException
	{
		Route route = new Route("AMS", "FRA");
		route.setFlights(loadFlights("AMS", "FRA"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
        fss.searchFlights(1, getDepartureDate(31));

        Map<String, BigDecimal> expectedResult = new LinkedHashMap<>();

        expectedResult.put("TK2372", new BigDecimal("157.6"));
        expectedResult.put("TK2659", new BigDecimal("198.4"));
        expectedResult.put("LH5909", new BigDecimal("90.4"));

        assertEquals(fss.getFlightResult(),expectedResult);
	}

	@Test
	public void getRoutesLhrToIst() throws LastMinuteException
	{
		Route route = new Route("LHR", "IST");
		route.setFlights(loadFlights("LHR", "IST"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.searchFlights(3, getDepartureDate(14));

		Map<String, BigDecimal> expectedResult = new LinkedHashMap<>();
		expectedResult.put("TK8891", new BigDecimal("900.0"));
		expectedResult.put("LH1085", new BigDecimal("532.8"));

		assertEquals(fss.getFlightResult(),expectedResult);
	}

	@Test
	public void getRoutesBcnToMad() throws LastMinuteException
	{
		Route route = new Route("BCN", "MAD");
		route.setFlights(loadFlights("BCN", "MAD"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.searchFlights(2, getDepartureDate(1));

		Map<String, BigDecimal> expectedResult = new LinkedHashMap<>();
		expectedResult.put("IB2171", new BigDecimal("777.0"));
		expectedResult.put("LH5496", new BigDecimal("879.0"));

		assertEquals(fss.getFlightResult(),expectedResult);
	}

	@Test
	public void setRoutesAmsToFraFromMadToBcn() throws LastMinuteException
	{
		Route route = new Route("MAD", "BCN");
		route.setOrigin("AMS");
		route.setDestination("FRA");

        assertEquals(route,new Route("AMS","FRA"));
	}

	@Rule
    public ExpectedException expectedEx3 = ExpectedException.none();

	@Test
	public void getRoutesCdgToFra() throws NotFoundException, LastMinuteException
	{
		expectedEx3.expect(NotFoundException.class);
        expectedEx3.expectMessage("No flights available for the defined route");

		Route route = new Route("CDG", "FRA");
		route.setFlights(loadFlights("CDG", "FRA"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.getFlightResult();
	}

	@Test(expected = LastMinuteException.class)
	public void shouldThrowLastMinuteExceptionWhenOriginIsNotDefined() throws LastMinuteException
	{
		Route route = new Route("","MAD");
		route.setFlights(loadFlights("", "MAD"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.searchFlights(2, getDepartureDate(1));
	}

	@Test(expected = LastMinuteException.class)
	public void shouldThrowLastMinuteExceptionWhenDestinationIsNotDefined() throws LastMinuteException
	{
		Route route = new Route("BCN","");
		route.setFlights(loadFlights("BCN", ""));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.searchFlights(2, getDepartureDate(1));
	}

	@Rule
    public ExpectedException expectedEx1 = ExpectedException.none();

	@Test
	public void shouldThrowLastMinuteExceptionWhenDateIsAheadTheCurrentDate() throws LastMinuteException
	{
		expectedEx1.expect(LastMinuteException.class);
        expectedEx1.expectMessage("The departure date is mandatory and it should be ahead the current date");

		Route route = new Route("BCN", "MAD");
		route.setFlights(loadFlights("BCN", "MAD"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.searchFlights(2, getDepartureDate(-2));
	}

	@Rule
    public ExpectedException expectedEx2 = ExpectedException.none();

	@Test
	public void shouldThrowLastMinuteExceptionWhenPassengersLessThanZero() throws LastMinuteException
	{
		expectedEx2.expect(LastMinuteException.class);
        expectedEx2.expectMessage("The number of passengers has to be above zero");

		Route route = new Route("BCN", "MAD");
		route.setFlights(loadFlights("BCN", "MAD"));
		FlightSearchingSystem fss = new FlightSearchingSystem(route);
		fss.searchFlights(-2, getDepartureDate(1));
	}

	/**
	 * Get the departure date set based on the days provided
	 * @param dayOfDeparture
	 * @return
	 */
	public Date getDepartureDate (int dayOfDeparture) {
		Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, (dayOfDeparture + 1));
        return c.getTime();
    }

	/**
	 * Load from the csv file the flights available for the particular route
	 * @param origin
	 * @param destination
	 * @return
	 * @throws LastMinuteException
	 */
	public List<Flight> loadFlights(String origin, String destination) throws LastMinuteException
	{
		List<Flight> flights = new ArrayList<Flight>();

		List<List<String>> allRoutes = readAllRecords(fullPathTo("flight-routes.csv"));
		List<List<String>> allPrices = readAllRecords(fullPathTo("flight-prices.csv"));

		for (int i = 0; i < allRoutes.size(); i++) {
			if(allRoutes.get(i).get(0).contains(origin) && allRoutes.get(i).get(1).contains(destination))
			{
				String routeCode = allRoutes.get(i).get(2);

				for (int k = 0; k < allPrices.size(); k++) {
					if(allPrices.get(k).get(0).equals(routeCode))
					{
						BigDecimal routePrice = new BigDecimal (allPrices.get(k).get(1));
						Flight fl = new Flight(routeCode, routePrice);
						flights.add(fl);
					}
				}
			}
		}
		return flights;
	}

	private String fullPathTo(String fileName)
	{
		return getClass().getClassLoader().getResource(fileName).getPath();
	}
}

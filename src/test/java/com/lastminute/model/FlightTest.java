package com.lastminute.model;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lastminute.exception.LastMinuteException;

import static org.junit.Assert.*;

import java.math.BigDecimal;

public class FlightTest {

	 @Test
	 public void getFlightCode() throws Exception {
	     Flight flight = new Flight("AAAA", new BigDecimal("10"));
	     assertTrue(flight.getFlightCode().equals("AAAA"));
	 }

     @Test
     public void getFlightPrice() throws Exception {
    	 Flight flight = new Flight("AAAA", new BigDecimal("10"));
	     assertTrue(flight.getPrice().compareTo(BigDecimal.TEN) == 0);
     }

     @Test
     public void checkIfTwoFlightAreTheSame() throws Exception {
    	 Flight flight1 = new Flight("AAAA", new BigDecimal("10"));
    	 Flight flight2 = new Flight("AAAA", new BigDecimal("10"));
	     assertTrue(flight1.equals(flight2));
     }

     @Rule
     public ExpectedException expectedEx = ExpectedException.none();

     @Test
     public void shouldThrowLastMinuteExceptionWhenCreatingFlightWitOutFlightCode() throws Exception {
         expectedEx.expect(LastMinuteException.class);
         expectedEx.expectMessage("Flight code cannot be omitted");
         Flight flight = new Flight("",new BigDecimal("10.0"));
     }

     @Test
     public void shouldThrowLastMinuteExceptionWhenCreatingFlightWithPriceLessThanZero() throws Exception {
         expectedEx.expect(LastMinuteException.class);
         expectedEx.expectMessage("Price must be greather than zero");
         Flight flight = new Flight("CIA",new BigDecimal("-4"));
     }
}
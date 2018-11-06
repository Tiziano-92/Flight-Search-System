package com.lastminute.model;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lastminute.exception.LastMinuteException;
import com.lastminute.model.Route;

import static org.junit.Assert.*;

public class RouteTest {

	 @Test
	 public void getOrigin() throws Exception {
	     Route route = new Route("AAAA", "LLL");
	     assertTrue(route.getOrigin().equals("AAAA"));
	 }

     @Test
     public void getDestination() throws Exception {
         Route route = new Route("AAAA", "FFF");
         assertTrue(route.getOrigin().equals("AAAA"));
     }

     @Test
     public void checkIfTwoRouteAreTheSame() throws Exception {
         Route route1 = new Route("AAAA", "FFF");
         Route route2 = new Route("AAAA", "FFF");
         assertTrue(route1.equals(route2));
     }

     @Rule
     public ExpectedException expectedEx = ExpectedException.none();

     @Test
     public void shouldThrowIllegalArgumentExceptionWhenCreatingRouteWithNullValues() throws Exception {
         expectedEx.expect(IllegalArgumentException.class);
         expectedEx.expectMessage("Origin and destination have to be given!");
         Route route = new Route("","");
     }
}
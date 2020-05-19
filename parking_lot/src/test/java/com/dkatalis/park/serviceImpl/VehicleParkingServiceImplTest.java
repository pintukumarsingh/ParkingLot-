package com.dkatalis.park.serviceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import com.dkatalis.park.exception.ErrorCode;
import com.dkatalis.park.exception.ParkingException;
import com.dkatalis.park.model.Car;
import com.dkatalis.park.service.VehicleParkingService;

public class VehicleParkingServiceImplTest
{
	
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;

	private ByteArrayInputStream testIn;
	private ByteArrayOutputStream testOut;
	VehicleParkingService vehicleParkingService;

	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private String getOutput() {
		return testOut.toString();
	}

	@BeforeEach
	public void setUpOutput() {
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));
		vehicleParkingService = new VehicleParkingServiceImpl();
	}

	@After
	public void restoreSystemInputOutput() {
		System.setIn(systemIn);
		System.setOut(systemOut);
	}
	
	@Test
	public void VehicleParkingService_createParkingLot_test() throws ParkingException
	{
		vehicleParkingService.createParkingLot(3);
		assertTrue("createdparkinglotwith3slots".equalsIgnoreCase(getOutput().trim().replace(" ", "")));
	}
		
	@Test
	public void VehicleParkingService_test_leave() throws Exception
	{
		exception.expect(ParkingException.class);
		exception.expectMessage(is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));
		ParkingException ex = assertThrows(ParkingException.class,() -> vehicleParkingService.unPark("KA-01-HH-2701", 2));
		assertEquals("Sorry, Car Parking Does not Exist", ex.getMessage());
        assertEquals(ParkingException.class, ex.getClass());
	}

	@Test
	public void VehicleParkingService_test_createParkingLot() throws ParkingException{
		
		  vehicleParkingService.createParkingLot(1); 
		  vehicleParkingService.park(new Car("KA-01-HH-1234"));
		  vehicleParkingService.unPark("KA-01-HH-1234", 1);
		  assertEquals( "Createdparkinglotwith1slots\nAllocatedslotnumber:1\nRegistrationnumberKA-01-HH-1234withSlotNumber1isfreewithCharge10".replaceAll("\\n|\\r\\n", System.getProperty("line.separator")), getOutput().trim().replace(" ", ""));
		 
	}
	
	@Test
	public void VehicleParkingService_test_calculateTotalParkingCharge_test() {
		int actualParkingCharge = vehicleParkingService.calculateTotalParkingCharge(2);
		assertEquals(10, actualParkingCharge);
	}
	
	@Test
	public void VehicleParkingService_test_calculateTotalParkingCharge_invalid_test() {
		int actualParkingCharge = vehicleParkingService.calculateTotalParkingCharge(-5);
		assertEquals(0, actualParkingCharge);
	}

}

package com.dkatalis.park.repositryImpl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.dkatalis.park.model.Car;
import com.dkatalis.park.model.Parking;
import com.dkatalis.park.model.ParkingImpl;
import com.dkatalis.park.model.Vehicle;


public class ParkingManagementTest {

	ParkingManagement<Vehicle> car ;
	
	@BeforeEach
	public void setUpOutput() {
		car = ParkingManagement.getInstance(2, new ParkingImpl());
	}


	@AfterEach
	public void restoreSystemInputOutput() {
		ParkingManagement.getInstance(0, null);
		car = null;
	}
	
	
	@Test
	public void ParkingManagement_test_leaveCar() {
		car.parkCar(new Car("KA-01-HH-7777"));
		boolean flag = car.leaveCar(1);
		assertTrue(flag==true);
	}
	
	@Test
	public void ParkingManagement_test_getStatus() {
		List<String> list =	car.getStatus();
		assertTrue(list != null);
		
	}
	
	@Test
	public void ParkingManagement_test_getSlotNoFromRegistrationNo() {
		car.parkCar(new Car("KA-01-HH-7771"));
		int slot = car.getSlotNoFromRegistrationNo("KA-01-HH-7771");
		assertTrue(slot==1);
		car.leaveCar(1);
	}
	
	@Test
	public void ParkingManagement_test_parkCar() {
		
		int slot =car.parkCar(new Car("KA-01-HH-7775"));
		assertTrue(slot==1);
	}
	
}

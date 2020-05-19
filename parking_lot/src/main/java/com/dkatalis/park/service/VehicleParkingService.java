package com.dkatalis.park.service;

import java.util.Optional;

import com.dkatalis.park.exception.ParkingException;
import com.dkatalis.park.model.Vehicle;

public interface VehicleParkingService
{
	public void createParkingLot(int capacity) throws ParkingException;
	
	public Optional<Integer> park(Vehicle vehicle) throws ParkingException;
	
	public void unPark(String vehicleNumber, int parkingHour) throws ParkingException;
	
	public Optional<Integer> getAvailableSlotsCount() throws ParkingException;
	
	public void getStatus() throws ParkingException;
	
	public int calculateTotalParkingCharge(int parkingHours);
}

package com.dkatalis.park.repositry;

import java.util.List;

import com.dkatalis.park.model.Vehicle;

public interface ParkingDataManager<T extends Vehicle>
{
	public int parkCar(T vehicle);
	
	public boolean leaveCar(int slotNumber);
	
	public List<String> getStatus();
	
	public int getAvailableSlotsCount();
	
	public int getSlotNoFromRegistrationNo(String registrationNo);
}
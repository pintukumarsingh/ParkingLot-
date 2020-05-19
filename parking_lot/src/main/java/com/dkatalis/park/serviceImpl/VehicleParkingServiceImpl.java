package com.dkatalis.park.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.dkatalis.park.exception.ErrorCode;
import com.dkatalis.park.exception.ParkingException;
import com.dkatalis.park.model.ParkingImpl;
import com.dkatalis.park.model.Vehicle;
import com.dkatalis.park.repositry.ParkingDataManager;
import com.dkatalis.park.repositryImpl.ParkingManagement;
import com.dkatalis.park.service.VehicleParkingService;
import com.dkatalis.park.util.ParkingConstant;

public class VehicleParkingServiceImpl implements VehicleParkingService
{
	private ParkingDataManager<Vehicle> dataManager = null;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Override
	public void createParkingLot(int capacity) throws ParkingException
	{
		if (dataManager != null) {
			throw new ParkingException(ErrorCode.PARKING_ALREADY_EXIST.getMessage());
		}
		this.dataManager = ParkingManagement.getInstance(capacity, new ParkingImpl());
		System.out.println("Created parking lot with " + capacity + " slots");
	}
	
	@Override
	public Optional<Integer> park(Vehicle vehicle) throws ParkingException
	{
		Optional<Integer> value = Optional.empty();
		lock.writeLock().lock();
		validateParkingLot();
		try
		{
			value = Optional.of(dataManager.parkCar(vehicle));
			if (value.get() == ParkingConstant.NOT_AVAILABLE)
				System.out.println("Sorry, parking lot is full");
			else if (value.get() == ParkingConstant.VEHICLE_ALREADY_EXIST)
				System.out.println("Sorry, vehicle is already parked.");
			else
				System.out.println("Allocated slot number: " + value.get());
		}
		catch (Exception e)
		{
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally
		{
			lock.writeLock().unlock();
		}
		return value;
	}
	
	
	private void validateParkingLot() throws ParkingException
	{
		if (dataManager == null)
		{
			throw new ParkingException(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
		}
	}
	
	@Override
	public void unPark(String vehicleNumber, int parkingHours) throws ParkingException
	{
		lock.writeLock().lock();
		validateParkingLot();
		try
		{
			int slotNumber = dataManager.getSlotNoFromRegistrationNo(vehicleNumber);
			if(slotNumber > 0) {
				if (dataManager.leaveCar(slotNumber)) {
					System.out.println("Registration number "+ vehicleNumber +" with Slot Number " + slotNumber + " is free with Charge " + calculateTotalParkingCharge(parkingHours));
				}
				else {
					System.out.println("Slot number is Empty Already.");
				}
			}else {
				switch(slotNumber) {
				
				case -1 : System.out.println("Registration number "+ vehicleNumber + "not found");
				}
			}
			
		}
		catch (Exception e)
		{
			throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"), e);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}
	@Override
	public int calculateTotalParkingCharge(int parkingHours) {
		int total = 10;
		if(parkingHours > 0 && parkingHours <=2 ) {
			return total;
		}else if(parkingHours >2 ) {

			parkingHours = parkingHours -2;
			total = total + parkingHours*10;
			return total;
		}else
			return 0;
	}
	
	@Override
	public Optional<Integer> getAvailableSlotsCount() throws ParkingException
	{
		lock.readLock().lock();
		Optional<Integer> value = Optional.empty();
		validateParkingLot();
		try
		{
			value = Optional.of(dataManager.getAvailableSlotsCount());
		}
		catch (Exception e)
		{
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally
		{
			lock.readLock().unlock();
		}
		return value;
	}
	
	@Override
	public void getStatus() throws ParkingException
	{
		lock.readLock().lock();
		validateParkingLot();
		try
		{
			System.out.println("Slot No.\tRegistration No.");
			List<String> statusList = dataManager.getStatus();
			if (statusList.size() == 0)
				System.out.println("Sorry, parking lot is empty.");
			else
			{
				for (String statusSting : statusList)
				{
					System.out.println(statusSting);
				}
			}
		}
		catch (Exception e)
		{
			throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}
}

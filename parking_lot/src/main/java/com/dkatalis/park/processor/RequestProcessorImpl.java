package com.dkatalis.park.processor;

import com.dkatalis.park.exception.ErrorCode;
import com.dkatalis.park.exception.ParkingException;
import com.dkatalis.park.model.Car;
import com.dkatalis.park.service.VehicleParkingService;
import com.dkatalis.park.util.ParkingConstant;

public class RequestProcessorImpl implements RequestProcessor{

private VehicleParkingService parkingService;
	
	public void setParkingService(VehicleParkingService parkingService) throws ParkingException
	{
		this.parkingService = parkingService;
	}
	
	@Override
	public void execute(String input) throws ParkingException
	{
		String[] inputs = input.split(" ");
		String key = inputs[0];
		switch (key)
		{
			case ParkingConstant.CREATE_PARKING_LOT:
				try
				{
					parkingService.createParkingLot(Integer.parseInt(inputs[1]));
				}
				catch (NumberFormatException e)
				{
					throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
				}
				break;
			case ParkingConstant.PARK:
				parkingService.park(new Car(inputs[1]));
				break;
			case ParkingConstant.LEAVE:
				try
				{	
					String vehicleNumber = inputs[1];
					int parkingHours = Integer.parseInt(inputs[2]);
					parkingService.unPark( vehicleNumber, parkingHours);
				}
				catch (NumberFormatException e)
				{
					throw new ParkingException(
							ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"));
				}
				break;
			case ParkingConstant.STATUS:
				parkingService.getStatus();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void setService(VehicleParkingService  service)
	{
		this.parkingService = (VehicleParkingService) service;
	}
}

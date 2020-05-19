package com.dkatalis.park.processor;

import com.dkatalis.park.exception.ParkingException;
import com.dkatalis.park.service.VehicleParkingService;
import com.dkatalis.park.util.CommandInputMap;

public interface RequestProcessor {

public void setService(VehicleParkingService service);
	
	public void execute(String action) throws ParkingException;
	
	public default boolean validate(String inputString)
	{
		
		boolean valid = true;
		try
		{
			String[] inputs = inputString.split(" ");
			int params = CommandInputMap.getCommandsParameterMap().get(inputs[0]);
			switch (inputs.length)
			{
				case 1:
					if (params != 0) // e.g status -> inputs = 1
						valid = false;
					break;
				case 2:
					if (params != 1) // create_parking_lot 6 and park KA-01-HH-2701-> inputs = 2
						valid = false;
					break;
				case 3:
					if (params != 2) // leave KA-01-HH-3141 4 -> inputs = 3
						valid = false;
					break;
				default:
					valid = false;
			}
		}
		catch (Exception e)
		{
			valid = false;
		}
		return valid;
	}
}

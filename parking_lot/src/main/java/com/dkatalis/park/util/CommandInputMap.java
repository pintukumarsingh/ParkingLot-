package com.dkatalis.park.util;

import java.util.HashMap;
import java.util.Map;

public class CommandInputMap
{
	private static volatile Map<String, Integer> commandsParameterMap = new HashMap<String, Integer>();
	
	static
	{
		commandsParameterMap.put(ParkingConstant.CREATE_PARKING_LOT, 1);
		commandsParameterMap.put(ParkingConstant.PARK, 1);
		commandsParameterMap.put(ParkingConstant.LEAVE, 2);
		commandsParameterMap.put(ParkingConstant.STATUS, 0);
	}
	
	public static Map<String, Integer> getCommandsParameterMap()
	{
		return commandsParameterMap;
	}
	
	public static void addCommand(String command, int parameterCount)
	{
		commandsParameterMap.put(command, parameterCount);
	}
	
}

package com.dkatalis.park.model;



public abstract class Vehicle{

	private String vehicleNumber;
	private String vehicleColour;
	
	public Vehicle(String vehicleNumber)
	{
		this.vehicleNumber = vehicleNumber;
	}
	
	@Override
	public String toString()
	{
		return "[Registration No=" + vehicleNumber + ", color=" + vehicleColour + "]";
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleColour() {
		return vehicleColour;
	}

	public void setVehicleColour(String vehicleColour) {
		this.vehicleColour = vehicleColour;
	}	
}

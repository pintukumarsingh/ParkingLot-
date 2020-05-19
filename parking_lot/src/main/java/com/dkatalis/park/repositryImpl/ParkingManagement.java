package com.dkatalis.park.repositryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dkatalis.park.model.Parking;
import com.dkatalis.park.model.ParkingImpl;
import com.dkatalis.park.model.Vehicle;
import com.dkatalis.park.repositry.ParkingDataManager;
import com.dkatalis.park.util.ParkingConstant;


public class ParkingManagement<T extends Vehicle> implements ParkingDataManager<T>{

		private AtomicInteger capacity = new AtomicInteger();
		private AtomicInteger availability= new AtomicInteger();
		private Parking parking;
		private Map<Integer, Optional<T>> slotVehicleMap;
		
		@SuppressWarnings("rawtypes")
		private static ParkingManagement instance = null;
		
		@SuppressWarnings("unchecked")
		public static <T extends Vehicle> ParkingManagement<T> getInstance(int capacity,
				Parking parking)
		{
			if (instance == null)
			{
				synchronized (ParkingManagement.class)
				{
					if (instance == null)
					{
						instance = new ParkingManagement<T>(capacity, parking);
					}
				}
			}
			return instance;
		}
		
		private ParkingManagement(int capacity, Parking parking)
		{
			this.capacity.set(capacity);
			this.availability.set(capacity);
			this.parking = parking;
			if (parking == null)
				parking = new ParkingImpl();
			slotVehicleMap = new ConcurrentHashMap<>();
			for (int i = 1; i <= capacity; i++)
			{
				slotVehicleMap.put(i, Optional.empty());
				parking.add(i);
			}
		}
		
		@Override
		public int parkCar(T vehicle)
		{
			int availableSlot;
			if (availability.get() == 0)
			{
				return ParkingConstant.NOT_AVAILABLE;
			}
			else
			{
				availableSlot = parking.getSlot();
				if (slotVehicleMap.containsValue(Optional.of(vehicle)))
					return ParkingConstant.VEHICLE_ALREADY_EXIST;
				
				slotVehicleMap.put(availableSlot, Optional.of(vehicle));
				availability.decrementAndGet();
				parking.removeSlot(availableSlot);
			}
			return availableSlot;
		}
		
		@Override
		public boolean leaveCar(int slotNumber)
		{
			if (!slotVehicleMap.get(slotNumber).isPresent()) // Slot already empty
				return false;
			availability.incrementAndGet();
			parking.add(slotNumber);
			slotVehicleMap.put(slotNumber, Optional.empty());
			return true;
		}
		
		@Override
		public List<String> getStatus()
		{
			List<String> statusList = new ArrayList<>();
			for (int i = 1; i <= capacity.get(); i++)
			{
				Optional<T> vehicle = slotVehicleMap.get(i);
				if (vehicle.isPresent())
				{
					statusList.add(i + "\t\t" + vehicle.get().getVehicleNumber());
				}
			}
			return statusList;
		}
		
		public int getAvailableSlotsCount()
		{
			return availability.get();
		}
		
		@Override
		public int getSlotNoFromRegistrationNo(String registrationNo)
		{
			int result = ParkingConstant.NOT_FOUND;
			for (int i = 1; i <= capacity.get(); i++)
			{
				Optional<T> vehicle = slotVehicleMap.get(i);
				if (vehicle.isPresent() && registrationNo.equalsIgnoreCase(vehicle.get().getVehicleNumber()))
				{
					result = i;
				}
			}
			return result;
		}
}

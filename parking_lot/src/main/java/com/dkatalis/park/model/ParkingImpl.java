package com.dkatalis.park.model;

import java.util.TreeSet;

public class ParkingImpl implements Parking{

	private TreeSet<Integer> freeSlots;

	public ParkingImpl()
	{
		freeSlots = new TreeSet<Integer>();
	}

	@Override
	public void add(int i)
	{
		freeSlots.add(i);
	}

	@Override
	public int getSlot()
	{
		return freeSlots.first();
	}

	@Override
	public void removeSlot(int availableSlot)
	{
		freeSlots.remove(availableSlot);
	}
}

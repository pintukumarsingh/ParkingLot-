package com.dkatalis.park;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dkatalis.park.exception.ErrorCode;
import com.dkatalis.park.exception.ParkingException;
import com.dkatalis.park.processor.RequestProcessor;
import com.dkatalis.park.processor.RequestProcessorImpl;
import com.dkatalis.park.serviceImpl.VehicleParkingServiceImpl;

@SpringBootApplication
public class ParkingLotApplication {
	
	InputStreamReader getInputStreamReader() {
		InputStream inputStream = getClass().getResourceAsStream("/file_input.txt");
		return new InputStreamReader(inputStream);	
	}
	

	public static void main(String[] args) throws ParkingException {
		
		ParkingLotApplication parkingLotApplication = new ParkingLotApplication();
		SpringApplication.run(ParkingLotApplication.class, args);
		System.out.println("******Parking lot application online******");
		BufferedReader bufferReader = null;
		String input = null;
		RequestProcessor processor = new RequestProcessorImpl();
		processor.setService(new VehicleParkingServiceImpl());
		try {
			
			bufferReader = new BufferedReader(parkingLotApplication.getInputStreamReader());
			int lineNo = 1;
			while ((input = bufferReader.readLine()) != null)
			{
				input = input.trim();
				if (processor.validate(input))
				{
					try {
						processor.execute(input);
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
				}
				else
					System.out.println("Incorrect Command Found at line: " + lineNo + " ,Input: " + input);
				lineNo++;
			}	
		}
		catch (Exception exception) {
			throw new ParkingException(ErrorCode.INVALID_FILE.getMessage(),exception);
		}
		finally
		{
			try
			{
				if (bufferReader != null)
					bufferReader.close();
			}
			catch (IOException e){}
		}
	}
}


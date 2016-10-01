package com.Alec.Weather.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.Alec.Weather.Object.City;


/**
 * @author Alec Divito
 * 
 * */
public class Request {
	
	private final String API_KEY = ">>>INPUT_KEY<<<";
	// Call current weather data for one location
	private final String API_WEATHER = // city
			"http://api.openweathermap.org/data/2.5/weather?q=";
	// Call Forecast for 5 days / every 3 hours
	private final String API_FORECAST = //city+","+country code
			"http://api.openweathermap.org/data/2.5/forecast?q=";
	// Call Daily forecast for 1 - 16 Days
	private final String API_DAILY =// city name,country code&cnt={cnt}
			"http://api.openweathermap.org/data/2.5/forecast/daily?q=";
		
	protected City city;
	private int days;
	
	protected Request(City city, int days) {
		this.city = city;
		this.days = days;
	}
	
	protected BufferedReader getCurrentWeatherStream() 
	{
		BufferedReader reader = null;
		try {
			URL url = new URL(API_WEATHER + city.getCity() + API_KEY);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		} 
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
		return reader;
	}
	
	protected BufferedReader get5DayForcastStream() 
	{
		BufferedReader reader = null;
		try 
		{
			URL url = new URL(API_FORECAST + city.getCity() + "," +
					city.getCountryCode() + API_KEY);
			
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch (MalformedURLException e) {e.printStackTrace();} 
		catch (IOException e) 			{e.printStackTrace();}
		
		return reader;
	}
	
	protected BufferedReader getDailyForecastStream()
	{
		if(days > 16) 
			{ days = 16; }
		
		BufferedReader reader = null;
		
		try 
		{
			URL url = new URL(API_DAILY + city.getCity() + "," +
					city.getCountryCode() + "&cnt=" + days +  API_KEY);
			
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		} 
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
		return reader;
	}
	
	protected BufferedReader getTimeZoneStream(double lat, double lon, double timeStamp)
	{
		BufferedReader reader = null;
		String key = "&key=>>>INPUT_KEY<<<";
		String googleURL = "https://maps.googleapis.com/maps/api/timezone/json?location=";
		URL getFile;
		try {
			getFile = new URL( googleURL + lat + "," + lon + "&timestamp=" + timeStamp + key);
			reader = new BufferedReader( new InputStreamReader(getFile.openStream()));
		} 
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		return reader;
	}
	
}
package com.Alec.Weather.Logic;

import java.util.Observable;

import com.Alec.Weather.Object.City;
import com.Alec.Weather.database.AccessDatabase;


/**
 * @author Alec Divito
 * 
 * */
public class WeatherData extends Observable{
	
	private City city;
	
	public void measurementsChanged() {
		setChanged();
		notifyObservers();
	}
	
	public void setMeasurements(String cityName) {
		city = AccessDatabase.getInstance().getCity(cityName);
		measurementsChanged();
	}
	
	public City getCity() {
		return city;
	}
	

}

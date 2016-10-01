package com.Alec.Weather.Object;


/**
 * @author Alec Divito
 * 
 * */
public class City {
	
	private int id;
	private String city;
	private String code;
	private float lat;
	private float lon;
	
	public City(){}
	
	public City(int id, String city, String code, float lat, float lon)
	{
		this.id = id;
		this.city = city;
		this.code = code;
		this.lat = lat;
		this.lon = lon;
	}
	
	public int getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public String getCountryCode() {
		return code;
	}

	public float getLatitude() {
		return lat;
	}

	public float getLongitude() {
		return lon;
	}


}

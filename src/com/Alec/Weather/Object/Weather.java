package com.Alec.Weather.Object;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author Alec Divito
 * 
 * */
public class Weather {
	
	private static final double KEL_TO_CEL = 273.15;
	private static SimpleDateFormat sdf = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
	private static DecimalFormat df =
			new DecimalFormat("##.##");
	
	private String description;
	private long timeOfData;
	private double currentTemp;
	private double maxTemp;
	private double minTemp;
	private double windSpeed;
	private long cloudCoveragePercent;
	
	public Weather(String description, long timeOfData, double currentTemp,
			double maxTemp,double minTemp, double windSpeed,long cloudCoveragePercent) {
		this.timeOfData = timeOfData;
		this.description = description;
		this.currentTemp = currentTemp - KEL_TO_CEL;
		this.maxTemp = maxTemp - KEL_TO_CEL;
		this.minTemp = minTemp - KEL_TO_CEL;
		this.windSpeed = windSpeed;
		this.cloudCoveragePercent = cloudCoveragePercent;
	}
	
	public void setTimeZone(TimeZone timeZone) {
		sdf.setTimeZone(timeZone);
	}
	
	public String getFormatedTimeOfData() {
		return sdf.format(new Date(timeOfData * 1000L));
	}
	public int getDay() {
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
		int day = Integer.parseInt(dayFormat.format(new Date(timeOfData * 1000L)));
		return day;
	}
	
	public String getFormatedNum(double num) {
		return df.format(num);
	}

	public String getDescription() {
		return description;
	}

	public long getTimeOfData() {
		return timeOfData;
	}
	
	public double getCurrentTemp() {
		return currentTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public long getCloudCoveragePercent() {
		return cloudCoveragePercent;
	}
	
	public String toString() {
		return 	getFormatedTimeOfData() + "\n" +
				"Description: " + getDescription() + "\n" +
				"temp: " + getFormatedNum(getCurrentTemp()) + "\n" +
				"max: " + getFormatedNum(getMaxTemp()) + "\n" +
				"min: " + getFormatedNum(getMinTemp()) + "\n" +
				"wind speed: " + getWindSpeed() + "\n" +
				"Cloud Coverage %: " + getCloudCoveragePercent();
	}
	
}

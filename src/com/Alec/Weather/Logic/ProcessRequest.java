package com.Alec.Weather.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Alec.Weather.Object.City;
import com.Alec.Weather.Object.Weather;


/**
 * @author Alec Divito
 * 
 * */
public class ProcessRequest extends Request{
	
	private List<Weather> weathList =  new ArrayList<>();
	
	private JSONParser parser = new JSONParser();
	private TypeRequest request;

	public ProcessRequest(TypeRequest request, City city, int days) {
		super(city, days);
		this.request = request;
	}

	/* process the wanted request */
	public List<Weather> getWeather() {
		try {
			switch(request)
			{
			case CURRENT: 	 processCurrent();  break;
			case FORECAST: 	 processForecast(); break;
			case DAILY:	 	 processDaily();	break;
			case NO_REQUEST: // TODO: no city was found. did you mean xyz
				break;
			default: System.out.println("city not found"); break;
			}
		} 
		catch(IOException e) 	{ e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }
		return weathList;
	}
	
	private void processDaily() throws IOException, ParseException {
		BufferedReader in = getDailyForecastStream();
			
		// TODO: Solve the set time zone id issue
		JSONObject obj = (JSONObject) parser.parse(in);
		JSONArray list = (JSONArray) obj.get("list");
		for(int i = 0; i < list.size(); i++) {
			JSONObject day = (JSONObject) list.get(i);
			String desc = getDecription((JSONArray) day.get("weather"));
			long   date = (long) day.get("dt");
			double  wind = (double)day.get("speed");
			long  clouds = (long) day.get("clouds");
			
			JSONObject temp = (JSONObject) day.get("temp");
			Number current = (Number)temp.get("day");
			Number max 	   = (Number)temp.get("max");
			Number min     = (Number)temp.get("min");
			
			Weather weatherObj = new Weather(desc, date, current.doubleValue(), 
							max.doubleValue(), min.doubleValue(), wind, clouds);
			if(i == 0) {
				weatherObj.setTimeZone(setTimeZoneId(weatherObj.getTimeOfData()));
			}
			weathList.add(weatherObj);
		}	
		in.close();
	}

	private void processForecast() throws IOException, ParseException {
		BufferedReader in = get5DayForcastStream();
		JSONObject obj = (JSONObject) parser.parse(in);
		JSONArray list = (JSONArray) obj.get("list");
		
		for(int i = 0; i < list.size(); i++) {
			JSONObject period = (JSONObject) list.get(i);
			String desc = getDecription((JSONArray) period.get("weather"));
			long   date = (long) period.get("dt");
				
			JSONObject main = (JSONObject) period.get("main");
			double current = (double)main.get("temp");
			double max 	   = (double)main.get("temp_max");
			double min     = (double)main.get("temp_min");
			
			JSONObject wind = (JSONObject) period.get("wind");
			double  windSpeed = (double) wind.get("speed");
			
			JSONObject clouds = (JSONObject) period.get("clouds");
			long cloudsPrct = (long) clouds.get("all");
			
			Weather weatherObj = new Weather(desc, date, current, max, min, windSpeed, cloudsPrct);
			if(i == 0) {
				weatherObj.setTimeZone(setTimeZoneId(weatherObj.getTimeOfData()));
			}
			weathList.add(weatherObj);
		}
		in.close();
	}

	/* Print out the current weather */
	private void processCurrent() throws IOException, ParseException{
		BufferedReader in = getCurrentWeatherStream();
		
		JSONObject obj = (JSONObject) parser.parse(in);
		String desc = getDecription((JSONArray) obj.get("weather"));
		long   date = (long) obj.get("dt");
		
		JSONObject wind = (JSONObject) obj.get("wind");
		double  windSpeed = (double) wind.get("speed");
		
		JSONObject clouds = (JSONObject) obj.get("clouds");
		long cloudsPrct = (long) clouds.get("all");
		
		// access main and get temputers
		JSONObject main = (JSONObject) obj.get("main");
		double current = (double)main.get("temp");
		double max 	   = (double)main.get("temp_max");
		double min     = (double)main.get("temp_min");

		Weather weatherObj = new Weather(desc, date, current, max, min, windSpeed, cloudsPrct);
		weatherObj.setTimeZone(setTimeZoneId(weatherObj.getTimeOfData()));
		weathList.add(weatherObj);
 
		in.close();
	}
	
	private String getDecription(JSONArray weather) {
		for(Object o : weather) {
			JSONObject loc = (JSONObject) o;
			return (String) loc.get("description");
		}	
		return "Not Found";
	}
	
	
	private TimeZone setTimeZoneId(long timeStamp) {		
		// Set up a formats to display text
		String timeZone = "";
		try(BufferedReader in = getTimeZoneStream(city.getLatitude(), city.getLongitude(), timeStamp))
		{
			JSONObject obj = (JSONObject) parser.parse(in);
			timeZone = (String) obj.get("timeZoneId");
			if(timeZone == null) {
				return TimeZone.getDefault();
			}
		} 
		catch (IOException e) 		{e.printStackTrace();}
		catch (ParseException e) 	{e.printStackTrace();}
		return TimeZone.getTimeZone(timeZone);
	}

	
}
package com.Alec.Weather.Controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.Alec.Weather.Logic.ProcessRequest;
import com.Alec.Weather.Logic.TypeRequest;
import com.Alec.Weather.Logic.WeatherData;
import com.Alec.Weather.Object.City;
import com.Alec.Weather.Object.Weather;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


/**
 * @author Alec Divito
 * 
 * */
public class Graph implements Observer {
	
	private Observable observable;
	private City city;
    private Axis<String> xAxis;
    private NumberAxis yAxis;
	private final AreaChart<String,Number> chart;
	
	public Graph(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
		
		this.xAxis = new CategoryAxis();
		this.yAxis = new NumberAxis();
		this.chart = new AreaChart<>(xAxis,yAxis);
        chart.setPrefWidth(720);
	}

	@SuppressWarnings("unchecked")
	public Node getWeatherGraph() {	
        return chart;
	}
	
	/** Return the day and the hour */
	private String getDay(long timeStamp) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date(timeStamp));
    	int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	
    	@SuppressWarnings("deprecation")
		int timePortion = cal.getTime().getHours();
    	
    	String day = "";	
    	switch(dayOfWeek) {
        	case Calendar.SUNDAY:	return "Sun " + dayOfMonth + " at " + timePortion;	
        	case Calendar.MONDAY:	return "Mon " + dayOfMonth + " at " + timePortion;	
        	case Calendar.TUESDAY:	return "Tues " + dayOfMonth + " at " + timePortion;	
        	case Calendar.WEDNESDAY:return "Wed " + dayOfMonth + " at " + timePortion;	
        	case Calendar.THURSDAY:	return "Thurs " + dayOfMonth + " at " + timePortion;	
        	case Calendar.FRIDAY:	return "Fri " + dayOfMonth + " at " + timePortion;	
        	case Calendar.SATURDAY:	return "Sat " + dayOfMonth + " at " + timePortion;	
    	}
    	return day;
	}

	@SuppressWarnings("unchecked")
	private void updateGraph() {
		ProcessRequest pr = new ProcessRequest(TypeRequest.DAILY, city, 14);
		List<Weather> list = pr.getWeather();
		
		if(chart.getData().size() > 0 ) {
			chart.getData().clear();
		}
		
        chart.setTitle("Temperature Monitoring in " + city.getCity() + " (in Degrees C)");
 
        XYChart.Series<String, Number> CityTemp= new XYChart.Series<>();
        XYChart.Series<String, Number> CityMaxTemp= new XYChart.Series<>();
        XYChart.Series<String, Number> CityMinTemp= new XYChart.Series<>();

        CityTemp.setName(city.getCity() +" Temputer");
        CityMaxTemp.setName(city.getCity() + " Max Temputer");
        CityMinTemp.setName(city.getCity() + " Min Temputer");
        for(Weather w : list) {
        	String day = getDay(w.getTimeOfData()* 1000L);
 	
        	CityTemp.getData().add(new XYChart.Data<String, Number>(day, w.getCurrentTemp()));
        	CityMaxTemp.getData().add(new XYChart.Data<String, Number>(day, w.getMaxTemp()));
        	CityMinTemp.getData().add(new XYChart.Data<String, Number>(day, w.getMinTemp()));
        }
        
        chart.getData().addAll(CityTemp, CityMaxTemp, CityMinTemp);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof WeatherData) {
			WeatherData weatherData = (WeatherData)o;
			this.city = weatherData.getCity();
		}
		updateGraph();
	}

}

package com.Alec.Weather.Controller;

import com.Alec.Weather.Logic.WeatherData;
import com.Alec.Weather.database.AccessDatabase;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author Alec Divito
 * 
 * */
public class CityObserver extends WeatherData{
	
	private final HBox fp;
	private final TextField searchBox;
	private final Text		result;
	private final Button	showWeather;
	
	public CityObserver() {
		super();
		this.fp = new HBox();
		this.searchBox = new TextField("");
		this.result = new Text();
		this.showWeather = new Button("Show Weather");
		showWeather.setVisible(false);
		showWeather.setOnAction(e -> {
			showWeather.setVisible(false);
			setMeasurements(searchBox.getText());
			searchBox.setEditable(true);
		});
		
		
		Button search = new Button("Search");
		search.setOnAction(e -> {
			String text = searchBox.getText();
			if(text.length() < 3) {
				result.setStroke(Color.RED);
				result.setText("Search text must be more then 4 letters!");
			} else {
				AccessDatabase adb = AccessDatabase.getInstance();
				boolean doesCityExist = adb.doesExist(text);
				if(doesCityExist) { 
					result.setText(text + " exists!"); 
					showWeather.setVisible(true);
					searchBox.setEditable(false);
				}
				else { 
					result.setText(text + " doesn't exists!"); 
				}
			}
		});
		
		
		
		fp.getChildren().addAll(searchBox, search, result, showWeather);
		fp.setAlignment(Pos.CENTER);
		fp.setSpacing(10);
	}
	
	public Node getView() {
		return fp;
	}

	
	

}

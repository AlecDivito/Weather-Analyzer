package com.Alec.Weather;

import com.Alec.Weather.Controller.CityObserver;
import com.Alec.Weather.Controller.Graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Alec Divito
 * 
 * */
public class RunGraphics extends Application{
	
	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		CityObserver dataObserver = new CityObserver();
		Graph g = new Graph(dataObserver);
				
		BorderPane p = new BorderPane();
		p.setCenter(g.getWeatherGraph());
		p.setBottom(dataObserver.getView());
		Scene s = new Scene(p);
        stage.setTitle("Area Chart Sample");
		stage.setScene(s);
		stage.show();
		
	}

}

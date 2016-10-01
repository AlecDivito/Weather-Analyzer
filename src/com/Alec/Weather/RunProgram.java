package com.Alec.Weather;

import java.sql.SQLException;
import java.util.List;

import com.Alec.Weather.Logic.ProcessRequest;
import com.Alec.Weather.Logic.TypeRequest;
import com.Alec.Weather.Object.City;
import com.Alec.Weather.Object.Weather;
import com.Alec.Weather.database.AccessDatabase;


/**
 * @author Alec Divito
 * 
 * */
public class RunProgram {

	
	/* Starting method for running from the command line */
	public static void main(String[] args) {
		if(args.length == 0) { 
			System.out.println("you need to input a city as an argument!");
			System.exit(1);
		}
		AccessDatabase adb = AccessDatabase.getInstance();
		if(!adb.doesExist(args[0])){
			System.out.println("City you Requested does not exist!");
			System.exit(1);
		}
		City city = adb.getCity(args[0]);
		ProcessRequest pr = new ProcessRequest(TypeRequest.CURRENT, city, 4);
		List<Weather> wl = pr.getWeather();
		
		for(Weather w : wl){
			System.out.println(w.toString() + "\n");
		}
		
		try {
			adb.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

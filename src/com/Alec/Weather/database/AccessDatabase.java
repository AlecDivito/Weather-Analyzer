package com.Alec.Weather.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.Alec.Weather.Object.City;

import javafx.geometry.Point2D;

/**
 * @author Alec Divito
 * 
 * */
public class AccessDatabase {
	
	private static AccessDatabase adb = null;
	
	// Connect to the database
	private Connection connect;
	
	// Statment for executing queries
	private Statement stmt;
	
	private AccessDatabase()
	{		
		String url = "jdbc:mysql://127.0.0.1:3306/>>>INPUTDATABASE<<<"+
				"?verifyServerCertificate=false"+
				"&useSSL=false"+
				"&requireSSL=false";
		String user = "root"; // >>>INPUTDATABASEPASS<<<
		String pw   = "root"; // >>>INPUTDATABASEUSER<<<
		try {
			connect = DriverManager.getConnection
					(url, user, pw);
			stmt = connect.createStatement();
		} catch(SQLException e) {
			System.out.println("Failed to Connect to SQL Server");
		}
	}

	/** Find and create a City object if the city is stored
	 * inside the database else return an empty city Object */
	public City getCity(final String cityName)
	{
		if(cityName.length() > 46 || !doesExist(cityName)) { 
			return new City(); 
		}

		String queryString = "SELECT * FROM city_id WHERE city=?";
		City city = new City();
		try {
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setString(1, cityName);
			ResultSet rset = stmt.executeQuery();
			
			if(rset.next()) { 
				int id;
				String code ;
				float lat, lon;
				do {
					id = 	rset.getInt(1);
					code = 	rset.getString(3);
					lat = 	rset.getFloat(4);
					lon = 	rset.getFloat(5);
				} while(rset.next());
				city = new City(id, cityName, code, lat, lon);
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return city;
	}
	
	/* Return a list of locations that meet the criteria
	 * which is the area inside the lat and lon coordinats */
	public ArrayList<String> getCitysInsideCircle
		(Point2D longutideRange, Point2D latitudeRange) 
	{
		ArrayList<String> locationList = new ArrayList<>();
		String queryString = "SELECT city, country_code FROM city_id WHERE longitude > " +
				 longutideRange.getY() + " and longitude < " +
				 longutideRange.getX() + " and latitude > "+ 
				 latitudeRange.getY() +" and latitude < " +
				 latitudeRange.getX();
		try {
			ResultSet rset = stmt.executeQuery(queryString);
			String temp = "";
			while(rset.next()) 
			{
				temp = rset.getString(1);
				temp += ", ";
				temp += rset.getString(2);
				locationList.add(temp);
			}
		} 
		catch(SQLException ex) {ex.printStackTrace();}
		
		return locationList;
	}
	
	/* Search the database and return true or false if
	 * the city is contained inside the database*/
	public boolean doesExist(final String cityName) {
		String queryString = "SELECT 1 FROM city_id WHERE city=?";
		boolean exist = false;
		try {
			 PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setString(1, cityName);
			ResultSet rset = stmt.executeQuery();
			while(rset.next()) {
				exist = rset.getBoolean(1);
			}
		}
		catch (SQLException e) {e.printStackTrace();}
		return exist;
	}
	
	
	public void close() throws SQLException {
		stmt.close();
		connect.close();
		adb = null;
	}
	
	public static AccessDatabase getInstance() {
		if(adb == null) {
			adb = new AccessDatabase();
		}
		return adb;
	}

	
}

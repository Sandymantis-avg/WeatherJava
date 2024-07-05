package com.varna.util;
import com.varna.interfaces.DBConnector;
import com.varna.valueobj.*;

import java.sql.*;


public class MySQLDBConnector implements DBConnector {
  private Connection connect = null;
  private String url = "jdbc:mysql://localhost:3306/SearchHistory";
  private String user = "root";
  private String password = "aarav123";
  
  public MySQLDBConnector() {
    try {
      //Class.forName("com.mysql.cj.jdbc.Driver");
      if (connect == null) {
        connect = DriverManager.getConnection(url, user, password);  
      }
      System.out.println("Connection creation Success");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public Weather[] readWeatherData() {
    Statement statement = null;
    ResultSet resultSet = null;
    
    try {
      statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      // Execute USE statement to select the database
      statement.execute("USE SearchHistory");
      
      // Execute SELECT query to fetch data from the 'weather' table
      resultSet = statement.executeQuery("SELECT * FROM weather");
      
      //Takes this resultSet and calls the createWeatherData function
      Weather[] data = createWeatherObjects(resultSet); 
      
      return data;
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    //Default unreachable return statement
    return null;
  }
  
  public boolean writeWeatherData(Weather obj) {  
    Statement statement = null;
    String query = "INSERT INTO weather(location, temp_c, temp_f, windSpeed)" + 
        "VALUES(\"" + obj.getLocation() + "\"," + obj.getTemp_C() + ", " + obj.getTemp_F() + ", " + 
        obj.getWindSpeed() + ")";
    
    try {
      statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      statement.execute("USE SearchHistory");
      
      statement.executeUpdate(query);
      
      return true;
      
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }  
  }
  
  
  private Weather[] createWeatherObjects(ResultSet result) {
    Weather[] weatherList;
    
    int count = 0;
    int size = 20;
    
    try {
      if (result != null) {
        result.last();
        size = result.getRow();
        result.beforeFirst();
      }
      weatherList = new Weather[size];
      
      while (result.next()) {
        try {
        //Parse through the resultSet and get the following properties
          String location = result.getString("location");
          double temp_c = result.getDouble("temp_c");
          double temp_f = result.getDouble("temp_f");
          double windSpeed = result.getDouble("windSpeed");
          
          //Add the weather object to the array
          Weather weather = new Weather(location, temp_c, temp_f, windSpeed);
          weatherList[count] = weather;
          count++;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return weatherList;
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    //Unreachable
    return null;
  }
}
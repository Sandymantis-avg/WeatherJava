package com.varna;
import com.varna.util.*;
import com.varna.valueobj.Weather;
import com.varna.interfaces.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherApp {
  
  public static void main(String[] args) {
    WeatherApp obj = new WeatherApp();
    obj.createWeatherHistory("Cchennai", 75, 20, 15);
    obj.getWeatherHistory();
  }
  
  public String getWeatherHistory() {
    DBConnector DBObj = new MySQLDBConnector();
    Weather[] weatherInfo = DBObj.readWeatherData();
    JSONArray jsonArray = new JSONArray();
    
    for (Weather i : weatherInfo) {
      JSONObject json = new JSONObject();
      json.put("location", i.getLocation());
      json.put("temp_c", i.getTemp_C());
      json.put("temp_f", i.getTemp_F());
      json.put("WindSpeed", i.getWindSpeed());
      jsonArray.put(json);
    }
    
    System.out.println(jsonArray);
    return null;
  }
  
  public boolean createWeatherHistory(String location, double temp_c, double temp_f, double windSpeed) {
      DBConnector DBObj = new MySQLDBConnector();
      
      Weather weatherObj = new Weather(location, temp_c, temp_f, windSpeed);
      
      boolean status = DBObj.writeWeatherData(weatherObj);

      if (status) {
        System.out.println("Successfully entered");
      } else {
        System.out.println("Error entering data");
      }
  
      return status;
  }
}

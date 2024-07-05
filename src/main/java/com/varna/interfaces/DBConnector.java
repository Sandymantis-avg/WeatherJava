package com.varna.interfaces;
import com.varna.valueobj.Weather;

public interface DBConnector {
  
  public Weather[] readWeatherData();
  
  public boolean writeWeatherData(Weather obj);
}
  
  
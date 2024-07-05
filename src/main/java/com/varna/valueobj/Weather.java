package com.varna.valueobj;

public class Weather {
  private String location;
  private double temp_c;
  private double temp_f;
  private double windSpeed;
  
  public Weather(String location, double temp_c, double temp_f, double windSpeed) {
    this.location = location;
    this.temp_c = temp_c;
    this.temp_f = temp_f;
    this.windSpeed = windSpeed;
  }
  
  public String getLocation() {
    return location;
  }
  
  public double getTemp_C() {
    return temp_c;
  }
  
  public double getTemp_F() {
    return temp_f;
  }
  
  public double getWindSpeed() {
    return windSpeed;
  }
}

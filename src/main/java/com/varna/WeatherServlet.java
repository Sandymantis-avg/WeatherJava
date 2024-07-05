package com.varna;

import java.io.IOException;
import org.json.JSONObject;
import com.varna.util.MySQLDBConnector;
import com.varna.valueobj.Weather;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/history")
public class WeatherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        System.out.println("****************Inside doGet******************");
        String requestUrl = request.getRequestURI();
        System.out.println(requestUrl);
        
        MySQLDBConnector Db = new MySQLDBConnector();
        Weather[] weatherData = Db.readWeatherData();
        
        if (weatherData != null) {
          String json = "";
          json +=  "{\n\t" +  "\"WeatherData\" : [\n";
          for (int i = 0; i < weatherData.length; i++)
          {
              Weather obj = weatherData[i];
              json += "\t\t{\n";
              json += "\t\t\t" + "\"location\": " + JSONObject.quote(obj.getLocation()) + ",\n";
              json += "\t\t\t"+ "\"temp_c\": " + JSONObject.quote(obj.getTemp_C() + "") + ",\n";
              json += "\t\t\t" + "\"temp_f\": " + JSONObject.quote(obj.getTemp_F() + "") + "\n";
              json += "\t\t\t" + "\"windSpeed\": " + JSONObject.quote(obj.getWindSpeed() + "") + "\n";
              json += "\t\t}";
              
              if(i < weatherData.length - 1)
              {
                  json += ",\n";
              }
          }
          json += "\t\n\t]\n}";
          System.out.println(json);
          response.getOutputStream().println(json);
        } else {
          response.getOutputStream().println("{}");
        }
        
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      System.out.println("****************Inside doPost****************");
      response.setHeader("Access-Control-Allow-Origin", "*"); // Allow all origins
      MySQLDBConnector Db = new MySQLDBConnector();
      String location = request.getParameter("location");
      double temp_c = Double.parseDouble(request.getParameter("temp_c"));
      double temp_f = Double.parseDouble(request.getParameter("temp_f"));
      double windSpeed = Double.parseDouble(request.getParameter("windSpeed"));
      
      Weather weatherObj = new Weather(location, temp_c, temp_f, windSpeed);
      Db.writeWeatherData(weatherObj);
      
      response.getOutputStream().println("Weather data inserted succesfully");
    }
   
   
}

         
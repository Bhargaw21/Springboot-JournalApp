package com.myprojects.journal_app.Api_Response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    public Current current;

    @Data
    public class Current{

    public int temperature;
   
   @JsonProperty("weather_descriptions")
    public ArrayList<String> weatherDescriptions;
    
    public int humidity;
  
    public int feelslike;
    

}


}




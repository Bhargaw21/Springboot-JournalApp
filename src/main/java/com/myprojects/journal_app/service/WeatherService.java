package com.myprojects.journal_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myprojects.journal_app.Api_Response.WeatherResponse;
import com.myprojects.journal_app.cache.AppCache;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private  String apiKey ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String location) {
        String finalAPI = appCache.App_Cache.get("weather_api").replace("<CITY>", location).replace("<API_KEY>", apiKey);
          ResponseEntity<WeatherResponse>  response   = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse weatherResponse = response.getBody();
            return weatherResponse; 
    }
}

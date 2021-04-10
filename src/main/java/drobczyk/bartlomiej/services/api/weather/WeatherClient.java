package drobczyk.bartlomiej.services.api.weather;

import drobczyk.bartlomiej.model.dto.api.weather.OpenWeatherDto;
import drobczyk.bartlomiej.model.dto.api.weather.WeatherDto;
import drobczyk.bartlomiej.model.dto.api.weather.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherClient {
    public static final String API_KEY = "5a013a59a9de75639c72f77ed1f05a4a";
    private RestTemplate restTemplate;
    private String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";

    @Autowired
    public WeatherClient(RestTemplate template){
        this.restTemplate = template;
    }


    public WeatherDto getWeatherByLatAndLon(double lat,double lon){
        OpenWeatherDto openWeatherDto = restTemplate.getForObject
                (WEATHER_URL + "weather?lat={lat}&lon={lon}&appid={apiKey}&units=metric&lang=PL",
                        OpenWeatherDto.class,lat,lon,API_KEY);
        return WeatherMapper.toAppWeather(openWeatherDto);
    }



}

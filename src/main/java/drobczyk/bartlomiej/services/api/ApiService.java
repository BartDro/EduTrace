package drobczyk.bartlomiej.services.api;

import drobczyk.bartlomiej.model.dto.api.quote.QuoteDto;
import drobczyk.bartlomiej.model.dto.api.weather.WeatherDto;
import drobczyk.bartlomiej.services.api.location.LocationClient;
import drobczyk.bartlomiej.services.api.quote.QuoteClient;
import drobczyk.bartlomiej.services.api.weather.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    private LocationClient locationClient;
    private WeatherClient weatherClient;
    private QuoteClient quoteClient;

    @Autowired
    public ApiService(LocationClient locationClient, WeatherClient weatherClient, QuoteClient quoteClient) {
        this.locationClient = locationClient;
        this.weatherClient = weatherClient;
        this.quoteClient = quoteClient;
    }

    public WeatherDto provideWeather(){
        return weatherClient.getWeatherByLatAndLon(
                locationClient.getLocationInfo().getLatitude(),
                locationClient.getLocationInfo().getLongitude());
    }

    public QuoteDto provideQuote(){
        return quoteClient.getQuote();
    }
}

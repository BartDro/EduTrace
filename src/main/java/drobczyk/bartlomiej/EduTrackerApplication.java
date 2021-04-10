package drobczyk.bartlomiej;

import drobczyk.bartlomiej.services.api.location.LocationClient;
import drobczyk.bartlomiej.services.api.quote.QuoteClient;
import drobczyk.bartlomiej.services.api.weather.WeatherClient;

import drobczyk.bartlomiej.model.dto.api.location.LocationDto;
import drobczyk.bartlomiej.model.dto.api.quote.QuoteDto;
import drobczyk.bartlomiej.model.dto.api.weather.WeatherDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootApplication
public class EduTrackerApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(EduTrackerApplication.class, args);

        LocationDto location = ctx.getBean(LocationClient.class).getLocationInfo();
        WeatherDto weather = ctx.getBean(WeatherClient.class).getWeatherByLatAndLon(location.getLatitude(), location.getLongitude()); ;
        QuoteDto quoteDto = ctx.getBean(QuoteClient.class).getQuote();
        Date date = new Date(1617164539L*1000);
        int x =10;
    }
    @Bean
    public static RestTemplate restTemplate(){
        return new RestTemplate();
}
}

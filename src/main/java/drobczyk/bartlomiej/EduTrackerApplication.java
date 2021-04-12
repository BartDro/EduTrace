package drobczyk.bartlomiej;

import drobczyk.bartlomiej.services.api.location.LocationClient;
import drobczyk.bartlomiej.services.api.quote.QuoteClient;
import drobczyk.bartlomiej.services.api.weather.WeatherClient;

import drobczyk.bartlomiej.model.dto.api.location.LocationDto;
import drobczyk.bartlomiej.model.dto.api.quote.QuoteDto;
import drobczyk.bartlomiej.model.dto.api.weather.WeatherDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class EduTrackerApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(EduTrackerApplication.class, args);

    }
    @Bean
    public static RestTemplate restTemplate(){
        return new RestTemplate();
}
}

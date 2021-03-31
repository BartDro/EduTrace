package drobczyk.bartlomiej.services.api.quote;

import drobczyk.bartlomiej.model.dto.apis.quote.QuoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class QuoteClient {
    private RestTemplate restTemplate;

    @Autowired
    public QuoteClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public QuoteDto getQuote(){
       return restTemplate.getForObject("https://api.quotable.io/random",QuoteDto.class);
    }
}

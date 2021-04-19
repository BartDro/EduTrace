package drobczyk.bartlomiej.services.api.quote;

import drobczyk.bartlomiej.model.dto.api.quote.QuoteDto;
import drobczyk.bartlomiej.model.dto.api.quote.QuoteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteClient {
    private RestTemplate restTemplate;

    @Autowired
    public QuoteClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<QuoteDto> provideQuotes(){
        return restTemplate.getForObject("https://api.quotable.io/quotes?maxLength=130&limit=500", QuoteList.class)
                .getResults().stream()
                .map(x->new QuoteDto(x.getContent(),x.getAuthor()))
                .collect(Collectors.toList());
    }

    public List<QuoteDto> provideQuotes(){
        return restTemplate.getForObject("https://api.quotable.io/quotes?maxLength=130&limit=300",QuoteList.class)
                .getResults().stream()
                .map(x->new QuoteDto(x.getContent(),x.getAuthor()))
                .collect(Collectors.toList());
    }
}

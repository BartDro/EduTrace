package drobczyk.bartlomiej.model.dto.apis.quote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteDto {
    @JsonProperty("content")
    private String quote;
    private String author;

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }
}

package drobczyk.bartlomiej.model.dto.api.quote;

import java.util.List;

public class QuotesListDto {
    private List<QDto> results;

    public List<QDto> getResults() {
        return results;
    }

    public void setResults(List<QDto> results) {
        this.results = results;
    }
}

package edu.java.scrapper.http_clients;

import edu.java.scrapper.dto.QuestionUpdatesResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface StackOverflowClient {
    @GetExchange(value = "/questions/{ids}", accept = "application/json")
    public List<QuestionUpdatesResponse> getQuestionUpdates(@PathVariable String ids);
}

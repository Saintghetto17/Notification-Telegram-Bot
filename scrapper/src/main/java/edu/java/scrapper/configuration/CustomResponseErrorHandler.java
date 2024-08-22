package edu.java.scrapper.configuration;

import edu.java.bot.utils.exceptions.ChatNotFoundException;
import edu.java.bot.utils.exceptions.RepeatableCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        return (statusCode.is4xxClientError() || statusCode.is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();

        switch (statusCode) {
            case BAD_REQUEST:
                throw new IllegalArgumentException("Invalid parameters given to dto");
            case NOT_FOUND:
                throw new ChatNotFoundException("Chat not found");
            case CONFLICT:
                throw new RepeatableCreationException("Conflict due to repeatable creation");
            default:
                throw new RuntimeException("Unexpected server error");
        }
    }
}

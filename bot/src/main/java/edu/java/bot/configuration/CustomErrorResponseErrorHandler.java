package edu.java.bot.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomErrorResponseErrorHandler implements ResponseErrorHandler {
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
        }
    }
}

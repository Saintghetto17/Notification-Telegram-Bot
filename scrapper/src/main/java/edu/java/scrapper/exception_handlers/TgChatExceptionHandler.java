package edu.java.scrapper.exception_handlers;

import com.example.model.ApiErrorResponse;
import edu.java.scrapper.controllers.LinkController;
import edu.java.scrapper.controllers.TgChatController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice(
        basePackageClasses = {TgChatController.class},
        basePackages = "edu.java.scrapper.controllers")
@Slf4j
public class TgChatExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.description("Incorrect parameters")
                .code("400")
                .exceptionName("MethodArgumentNotValidException")
                .exceptionMessage(ex.getMessage())
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

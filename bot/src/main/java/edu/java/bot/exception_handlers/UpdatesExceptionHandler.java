package edu.java.bot.exception_handlers;

import com.example.model.ChatNotFoundErrorResponse;
import com.example.model.IncorrectParametersErrorResponse;
import com.example.model.RepeatableCrearionErrorResponse;
import edu.java.bot.controllers.UpdatesController;
import edu.java.bot.utils.exceptions.ChatNotFoundException;
import edu.java.bot.utils.exceptions.RepeatableCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;


@RestControllerAdvice(
        basePackageClasses = {UpdatesController.class},
        basePackages = "edu.java.bot.controllers")
public class UpdatesExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UpdatesExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<IncorrectParametersErrorResponse>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        IncorrectParametersErrorResponse errorResponse = new IncorrectParametersErrorResponse();
        errorResponse.description("Incorrect parameters")
                .code("400")
                .exceptionName("MethodArgumentNotValidException")
                .exceptionMessage(ex.getMessage())
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ChatNotFoundErrorResponse>
    handleMethodArgumentNotValidException(ChatNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ChatNotFoundErrorResponse errorResponse = new ChatNotFoundErrorResponse();
        errorResponse.description("Chat is not found")
                .code("404")
                .exceptionName("ChatNotFoundException")
                .exceptionMessage(ex.getMessage())
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepeatableCreationException.class)
    public ResponseEntity<RepeatableCrearionErrorResponse>
    handleMethodArgumentNotValidException(RepeatableCreationException ex) {
        log.error(ex.getMessage(), ex);
        RepeatableCrearionErrorResponse errorResponse = new RepeatableCrearionErrorResponse();
        errorResponse.description("You tried to create an instance again, but it is already created")
                .code("409")
                .exceptionName("RepeatableCreationException")
                .exceptionMessage(ex.getMessage())
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}

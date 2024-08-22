package edu.java.scrapper.controllers;


import com.example.api.TgChatApi;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TgChatController implements TgChatApi {

    @Override
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/tg-chat/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<Void> tgChatIdDelete(@Parameter(name = "id", description = "",
            required = true, in = ParameterIn.PATH) @PathVariable("id") Long id) {
        log.info("Deleting tgChatId {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/tg-chat/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<Void> tgChatIdPost(@Parameter(name = "id", description = "",
            required = true, in = ParameterIn.PATH) @PathVariable("id") Long id) {
        log.info("Posting tgChatId {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

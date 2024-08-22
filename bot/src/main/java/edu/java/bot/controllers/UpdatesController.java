package edu.java.bot.controllers;

import com.example.api.UpdatesApi;
import com.example.model.LinkUpdate;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class UpdatesController implements UpdatesApi {


    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/updates",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<Void> updatesPost(@Parameter(name = "LinkUpdate", description = "", required = true)
                                            @Valid @RequestBody LinkUpdate linkUpdate) {
        log.info("Updating chat {}", linkUpdate.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

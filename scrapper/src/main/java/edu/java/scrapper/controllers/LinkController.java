package edu.java.scrapper.controllers;

import com.example.api.LinksApi;
import com.example.model.AddLinkRequest;
import com.example.model.LinkResponse;
import com.example.model.ListLinksResponse;
import com.example.model.RemoveLinkRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkController implements LinksApi {


    @Override
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/links",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<LinkResponse>
    linksDelete(@NotNull
                @Parameter(name = "Tg-Chat-Id", description = "", required = true, in = ParameterIn.HEADER)
                @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
                @Parameter(name = "RemoveLinkRequest", description = "",
                        required = true) @Valid @RequestBody
                RemoveLinkRequest removeLinkRequest) {
        log.info("Delete link request: {}", removeLinkRequest.getLink());
        return new ResponseEntity<>(new LinkResponse().id(tgChatId).url(removeLinkRequest.getLink()), HttpStatus.OK);
    }

    @Override
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/links",
            produces = {"application/json"}
    )
    public ResponseEntity<ListLinksResponse> linksGet(@NotNull
                                                      @Parameter(name = "Tg-Chat-Id", description = "",
                                                              required = true, in = ParameterIn.HEADER)
                                                      @RequestHeader(value = "Tg-Chat-Id", required = true)
                                                      Long tgChatId) {
        log.info("Get links");
        ListLinksResponse listLinksResponse = new ListLinksResponse()
                .links(List.of(new LinkResponse()
                        .id(tgChatId)
                        .url(URI.create("http://localhost:8080"))));
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @Override
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/links",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<LinkResponse> linksPost(@NotNull
                                                  @Parameter(name = "Tg-Chat-Id", description = "", required = true,
                                                          in = ParameterIn.HEADER)
                                                  @RequestHeader(value = "Tg-Chat-Id", required = true)
                                                  Long tgChatId,
                                                  @Parameter(name = "AddLinkRequest", description = "", required = true)
                                                  @Valid @RequestBody AddLinkRequest addLinkRequest) {
        log.info("Link added {}", addLinkRequest.getLink());
        return new ResponseEntity<>(new LinkResponse().id(tgChatId)
                .url(addLinkRequest.getLink()), HttpStatus.OK);
    }
}

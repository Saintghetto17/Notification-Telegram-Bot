package edu.java.bot.http_clients;

import edu.java.dto.scrapper.AddLinkRequest;
import edu.java.dto.scrapper.LinkResponse;
import edu.java.dto.scrapper.ListLinksResponse;
import edu.java.dto.scrapper.RemoveLinkRequest;
import jdk.dynalink.linker.LinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {

    @PostExchange(value = "/tg-chat/{id}", accept = "application/json")
    ResponseEntity<Void> registrateChat(@PathVariable Long id);

    @DeleteExchange(value = "/tg-chat/{id}", accept = "application/json")
    ResponseEntity<Void> deleteChat(@PathVariable Long id);

    @DeleteExchange(value = "/links", accept = "application/json")
    ResponseEntity<LinkResponse> deleteLinks(Long id, RemoveLinkRequest removeLinkRequest);

    @GetExchange(value = "/links", accept = "application/json")
    ResponseEntity<ListLinksResponse> getLinks(Long id);

    @PostExchange(value = "/links", accept = "application/json")
    ResponseEntity<LinkResponse> linksPost(Long id, AddLinkRequest addLinkRequest);

}

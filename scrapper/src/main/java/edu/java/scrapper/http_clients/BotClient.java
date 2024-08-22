package edu.java.scrapper.http_clients;


import edu.java.dto.bot.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {

    @PostExchange(value = "/updates", accept = "application/json")
    ResponseEntity<Void> getBotUpdates(@RequestBody LinkUpdate linkUpdate);

}

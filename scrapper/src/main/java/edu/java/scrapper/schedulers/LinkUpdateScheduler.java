package edu.java.scrapper.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
public class LinkUpdateScheduler {

    @Scheduled(fixedDelayString = "#{@delay}")
    public void update() {
        log.info("Updating link updates");
    }
}

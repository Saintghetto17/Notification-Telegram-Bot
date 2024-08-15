package edu.java.bot.runners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.services.UserMessageService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BotListenerImpl implements BotImpl {

    private final TelegramBot bot;
    private final UserMessageService userMessageService;

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            SendMessage message = userMessageService.process(update);
            SendResponse sendResponse = bot.execute(message);
            log.info(sendResponse.message().toString());
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @PostConstruct
    @Override
    public void start() {
        bot.setUpdatesListener(this);
    }

    @PreDestroy
    @Override
    public void close() {
        bot.shutdown();
    }

}

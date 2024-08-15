package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.TrackedLinksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StartCommand implements Command {

    private final TrackedLinksRepository trackedLinksRepository;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Starts a Notification bot.";
    }

    @Override
    public SendMessage handle(Update update) {
        if (!trackedLinksRepository.isChatExists(update.message().chat().id())) {
            trackedLinksRepository.create(update.message().chat().id());
        }
        return new SendMessage(update.message().chat().id(), "Hello, this NotificationBot is used primarily \n" +
            "for tracking the updates of the content on resources \n" +
            "which you provide!");

    }
}

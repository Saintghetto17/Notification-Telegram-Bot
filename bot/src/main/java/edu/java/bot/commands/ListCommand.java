package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.TrackedLinksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListCommand implements Command {

    private final TrackedLinksRepository trackedLinksRepository;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Shows the list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        if (trackedLinksRepository.findAllLinks(update.message().chat().id()).isEmpty()) {
            return new SendMessage(update.message().chat().id(), "No links are tracked");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your links:\n");
        int numberOfLink = 1;
        for (String link : trackedLinksRepository.findAllLinks(update.message().chat().id())) {
            stringBuilder.append(numberOfLink).append(". ").append(link).append("\n");
            numberOfLink++;
        }
        return new SendMessage(update.message().chat().id(), stringBuilder.toString());
    }
}

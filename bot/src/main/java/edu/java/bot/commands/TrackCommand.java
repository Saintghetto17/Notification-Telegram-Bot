package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.TrackedLinksRepository;
import edu.java.bot.utils.LinkValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrackCommand implements Command {

    private final TrackedLinksRepository trackedLinksRepository;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start to track a new resource by link";
    }

    @Override
    public SendMessage handle(Update update) {

        String[] allTokens = update.message().text().replaceAll("\\s+", " ").split(" ");

        if (allTokens.length != 2) {
            return new SendMessage(update.message().chat().id(), "Please, provide a link. Only one link is allowed");
        }
        String link = allTokens[1];
        Long chatId = update.message().chat().id();

        if (!LinkValidator.isValidLinkURL(link)) {
            return new SendMessage(chatId, "Your link is invalid, please provide a valid URL");
        }

        if (trackedLinksRepository.isLinkExists(chatId, link)) {
            return new SendMessage(chatId, "This link is already tracked");
        }
        trackedLinksRepository.addLink(chatId, link);
        return new SendMessage(chatId, "Successfully added link");
    }
}

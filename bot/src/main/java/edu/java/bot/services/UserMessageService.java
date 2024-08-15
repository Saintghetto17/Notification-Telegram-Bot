package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserMessageService implements UserMessageProcessor {

    private static final String ERROR = "Sorry, this type of messages are not supported";
    private static final String COMMAND_NOT_FOUND = "Sorry, this type of commands are not supported";
    private final Map<String, Command> allCommands;

    @Autowired
    public UserMessageService(@Qualifier("allCommands") Map<String, Command> allCommands) {
        this.allCommands = allCommands;
    }

    @Override
    public Map<String, ? extends Command> commands() {
        return allCommands;
    }

    @Override
    public SendMessage process(Update update) {
        Long chatId = update.message().chat().id();
        if (!supports(update)) {
            return new SendMessage(chatId, ERROR);
        }
        String command = getCommand(update.message().text());
        if (!allCommands.containsKey(command)) {
            return new SendMessage(chatId, COMMAND_NOT_FOUND);
        }
        return allCommands.get(command).handle(update);
    }

    private boolean supports(Update update) {
        if (update.message() == null
            || update.message().text() == null
            || update.message().text().isBlank()) {
            return false;
        }
        return true;
    }

    private String getCommand(String text) {
        return text.split(" ")[0];
    }
}

package edu.java.bot.services;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.Map;

public interface UserMessageProcessor {
    Map<String, ? extends Command> commands();

    SendMessage process(Update update);
}

package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class HelpCommand implements Command {

    private static final String MESSAGE = "✨ Welcome to NotificationBot Help Center! ✨\n" +
        "\n" +
        "\uD83D\uDEE0\uFE0F **Available Commands:**\n" +
        "\n" +
        "1. **/start** - Starts a Notification bot. \n" +
        "2. **/list** - Shows the list of commands. \n" +
        "3. **/track** - Start to track a new resource by link. \n" +
        "4. **/untrack** - Stop to track the resource by link. \n" +
        "5. **/help** - The command that shows the help page. \n" +
        "\n" +
        "✨ *Thank you for using [NotificationBot]!* ✨";

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "The command that shows the help page.";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), MESSAGE);
    }
}

package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.repository.TrackedLinksRepository;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public TrackedLinksRepository trackedLinksRepository() {
        return new TrackedLinksRepository();
    }

    @Bean(name = "allCommands")
    public Map<String, Command> allCommands() {
        return Map.of(
            "/start", new StartCommand(trackedLinksRepository()),
            "/help", new HelpCommand(),
            "/track", new TrackCommand(trackedLinksRepository()),
            "/untrack", new UntrackCommand(trackedLinksRepository()),
            "/list", new ListCommand(trackedLinksRepository())
        );
    }

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(applicationConfig.telegramToken());
        Map<String, Command> commands = allCommands();
        bot.execute(new SetMyCommands(
            commands.get("/start").toApiCommand(),
            commands.get("/help").toApiCommand(),
            commands.get("/track").toApiCommand(),
            commands.get("/untrack").toApiCommand(),
            commands.get("/list").toApiCommand()
        ));
        return bot;
    }
}

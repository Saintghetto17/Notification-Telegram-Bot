package edu.java.bot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.repository.TrackedLinksRepository;
import edu.java.bot.services.UserMessageService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

/**
 * Most of the current tests should be spreaded into different methods, but I am lazy...
 * Also I got no idea WHY @Mock doesn't work?! It always tells me that mock is null.
 * Other bad scenarios are checked in Commands Tests !
 * @author Ilya Novitskiy
 */

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserMessageServiceTest {

    private final TrackedLinksRepository trackedLinksRepository = new TrackedLinksRepository();

    private final Map<String, Command> commands = Map.of(
        "/start", new StartCommand(trackedLinksRepository),
        "/help", new HelpCommand(),
        "/track", new TrackCommand(trackedLinksRepository),
        "/untrack", new UntrackCommand(trackedLinksRepository),
        "/list", new ListCommand(trackedLinksRepository)
    );

    private final UserMessageService userMessageService = new UserMessageService(commands);

    @Test
    public void UserMessageService_allCommands_Test() {
        Assertions.assertEquals(userMessageService.commands(), commands);
    }

    @Test
    public void UserMessageService_process_Test() {
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);

        Mockito.when(chat.id()).thenReturn((long) 1);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(update.message()).thenReturn(message);

        Mockito.when(message.text()).thenReturn("/start");

        Assertions.assertNotNull(userMessageService.process(update).getParameters());
        Assertions.assertEquals(
            userMessageService.process(update).getParameters().get("text"),
            "Hello, this NotificationBot is used primarily \n" +
                "for tracking the updates of the content on resources \n" +
                "which you provide!"
        );

        Mockito.when(message.text()).thenReturn("/help");
        Assertions.assertNotNull(userMessageService.process(update).getParameters());
        Assertions.assertEquals(
            userMessageService.process(update).getParameters().get("text"),
            "✨ Welcome to NotificationBot Help Center! ✨\n" +
                "\n" +
                "\uD83D\uDEE0\uFE0F **Available Commands:**\n" +
                "\n" +
                "1. **/start** - Starts a Notification bot. \n" +
                "2. **/list** - Shows the list of commands. \n" +
                "3. **/track** - Start to track a new resource by link. \n" +
                "4. **/untrack** - Stop to track the resource by link. \n" +
                "5. **/help** - The command that shows the help page. \n" +
                "\n" +
                "✨ *Thank you for using [NotificationBot]!* ✨"
        );

        trackedLinksRepository.create((long) 1);
        Mockito.when(message.text()).thenReturn("/track https://example.com");
        Assertions.assertNotNull(userMessageService.process(update).getParameters());
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(
            userMessageService.process(update).getParameters().get("text"),
            "Successfully added link"
        );

        trackedLinksRepository.create((long) 1);
        trackedLinksRepository.addLink((long) 1, "https://example.com");
        Mockito.when(message.text()).thenReturn("/untrack https://example.com");
        Assertions.assertEquals(
            userMessageService.process(update).getParameters().get("text"),
            "The link has been removed"
        );

        trackedLinksRepository.create((long) 1);
        trackedLinksRepository.addLink((long) 1, "https://example.com");
        trackedLinksRepository.addLink((long) 1, "https://youtube.com");
        Mockito.when(message.text()).thenReturn("/list");
        Assertions.assertEquals(userMessageService.process(update).getParameters().get("text"), "Your links:\n" +
            "1. https://example.com\n" +
            "2. https://youtube.com\n");

    }
}

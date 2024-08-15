package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.TrackedLinksRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Most of the current tests should be spreaded into different classes and into different methods, but I am lazy...
 * Also I got no idea WHY @Mock doesn't work?! It always tells me that mock is null.
 *
 * @author Ilya Novitskiy
 */

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommandsTest {

    @Test
    public void HelpCommandTest() {
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);

        String MESSAGE = "✨ Welcome to NotificationBot Help Center! ✨\n" +
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

        HelpCommand helpCommand = new HelpCommand();
        Assertions.assertEquals(helpCommand.command(), "/help");
        Assertions.assertEquals(helpCommand.description(), "The command that shows the help page.");

        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(update.message()).thenReturn(message);

        Assertions.assertEquals(helpCommand.handle(update).getParameters().get("chat_id"), (long) 1);
        Assertions.assertEquals(helpCommand.handle(update).getParameters().get("text"), MESSAGE);
    }

    @Test
    public void TrackCommandTest() {
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);

        TrackedLinksRepository trackedLinksRepository = new TrackedLinksRepository();
        trackedLinksRepository.create((long) 1);
        TrackCommand trackCommand = new TrackCommand(trackedLinksRepository);

        Assertions.assertEquals(trackCommand.command(), "/track");
        Assertions.assertEquals(trackCommand.description(), "Start to track a new resource by link");

        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/track https://example.com");
        Mockito.when(update.message()).thenReturn(message);

        Assertions.assertEquals(trackCommand.handle(update).getParameters().get("chat_id"), (long) 1);
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(
            trackCommand.handle(update).getParameters().get("text"),
            "Successfully added link"
        );

        Mockito.when(message.text()).thenReturn("dasdasdads asdasdasda asdasd asdasd");
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(
            trackCommand.handle(update).getParameters().get("text"),
            "Please, provide a link. Only one link is allowed"
        );

        Mockito.when(message.text()).thenReturn("/track sadasdasda");
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(trackCommand.handle(update).getParameters().get("text")
            , "Your link is invalid, please provide a valid URL");

        trackedLinksRepository.create((long) 1);
        trackedLinksRepository.addLink((long) 1, "https://example.com");
        Mockito.when(message.text()).thenReturn("/track https://example.com");
        Assertions.assertEquals(
            trackCommand.handle(update).getParameters().get("text"),
            "This link is already tracked"
        );
    }

    @Test
    public void UntrackCommandTest() {
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);

        TrackedLinksRepository trackedLinksRepository = new TrackedLinksRepository();
        UntrackCommand untrackCommand = new UntrackCommand(trackedLinksRepository);

        Assertions.assertEquals(untrackCommand.command(), "/untrack");
        Assertions.assertEquals(untrackCommand.description(), "Stop to track the resource by link");

        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/untrack https://example.com");
        Mockito.when(update.message()).thenReturn(message);

        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(untrackCommand.handle(update).getParameters().get("chat_id"), (long) 1);

        trackedLinksRepository.create((long) 1);
        trackedLinksRepository.addLink((long) 1, "https://example.com");
        Assertions.assertEquals(
            untrackCommand.handle(update).getParameters().get("text"),
            "The link has been removed"
        );

        Mockito.when(message.text()).thenReturn("dasdasdads asdasdasda asdasd asdasd");
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(
            untrackCommand.handle(update).getParameters().get("text"),
            "Please, provide a link. Only one link is allowed"
        );

        Mockito.when(message.text()).thenReturn("/untrack sadasdasda");
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(untrackCommand.handle(update).getParameters().get("text")
            , "Invalid link, please enter correct link in URL format");

        Mockito.when(message.text()).thenReturn("/untrack https://example.com");
        trackedLinksRepository.create((long) 1);
        Assertions.assertEquals(untrackCommand.handle(update).getParameters().get("text")
            , "This link is not tracked by you");
    }

    @Test
    public void StartCommandTest() {
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);

        StartCommand startCommand = new StartCommand(new TrackedLinksRepository());

        Assertions.assertEquals(startCommand.command(), "/start");
        Assertions.assertEquals(startCommand.description(), "Starts a Notification bot.");

        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(update.message()).thenReturn(message);
        Assertions.assertEquals(startCommand.handle(update).getParameters().get("chat_id"), (long) 1);
        Assertions.assertEquals(
            startCommand.handle(update).getParameters().get("text"),
            "Hello, this NotificationBot is used primarily \n" +
                "for tracking the updates of the content on resources \n" +
                "which you provide!"
        );
    }

    @Test
    public void ListCommandTest() {
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);

        TrackedLinksRepository trackedLinksRepository = new TrackedLinksRepository();
        ListCommand listCommand = new ListCommand(trackedLinksRepository);
        trackedLinksRepository.create((long) 1);

        Assertions.assertEquals(listCommand.command(), "/list");
        Assertions.assertEquals(listCommand.description(), "Shows the list of tracked links");

        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(update.message()).thenReturn(message);

        trackedLinksRepository.addLink(1L, "https://example1.com");
        trackedLinksRepository.addLink(1L, "https://example2.com");

        Assertions.assertEquals(
            listCommand.handle(update).getParameters().get("text"),
            "Your links:\n" +
                "1. https://example1.com\n" +
                "2. https://example2.com\n"
        );
    }

}

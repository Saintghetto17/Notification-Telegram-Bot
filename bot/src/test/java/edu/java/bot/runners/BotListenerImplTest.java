package edu.java.bot.runners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
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
import com.pengrad.telegrambot.response.SendResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BotListenerImplTest {

    @Test
    public void BotListenerImpl_process_Test() {
        TelegramBot bot = Mockito.mock(TelegramBot.class);
        SendResponse sendResponse = Mockito.mock(SendResponse.class);
        Mockito.when(bot.execute(Mockito.any(SendMessage.class))).thenReturn(sendResponse);
        Mockito.when(sendResponse.message()).thenReturn(new Message());
        Chat chat = Mockito.mock(Chat.class);
        TrackedLinksRepository trackedLinksRepository = new TrackedLinksRepository();

        BotListenerImpl botListener = new BotListenerImpl(bot, new UserMessageService(Map.of(
            "/start", new StartCommand(trackedLinksRepository),
            "/help", new HelpCommand(),
            "/track", new TrackCommand(trackedLinksRepository),
            "/untrack", new UntrackCommand(trackedLinksRepository),
            "/list", new ListCommand(trackedLinksRepository)
        )));

        List<String> commands =
            List.of("/start", "/track https://example.com", "/untrack https://example.com", "list", "/help");

        List<Update> updates = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Message message = Mockito.mock(Message.class);
            Update update = Mockito.mock(Update.class);
            Mockito.when(chat.id()).thenReturn((long) 1);
            Mockito.when(message.chat()).thenReturn(chat);
            Mockito.when(message.text()).thenReturn(commands.get(i));
            Mockito.when(update.message()).thenReturn(message);
            updates.add(update);
        }

        botListener.start();

        Assertions.assertEquals(botListener.process(updates), UpdatesListener.CONFIRMED_UPDATES_ALL);
        botListener.close();
    }
}

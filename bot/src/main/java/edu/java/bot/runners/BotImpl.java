package edu.java.bot.runners;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public interface BotImpl extends AutoCloseable, UpdatesListener {

    @Override
    int process(List<Update> updates);

    void start();

    @Override
    void close();
}

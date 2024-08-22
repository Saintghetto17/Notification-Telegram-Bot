package edu.java.bot.utils.exceptions;

import java.io.IOException;

public class ChatNotFoundException extends IOException {
    public ChatNotFoundException(String chatNotFound) {
        super(chatNotFound);
    }
}

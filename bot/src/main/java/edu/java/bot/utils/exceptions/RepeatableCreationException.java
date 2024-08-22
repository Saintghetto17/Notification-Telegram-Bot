package edu.java.bot.utils.exceptions;

import java.io.IOException;

public class RepeatableCreationException extends IOException {
    public RepeatableCreationException(String conflictDueToRepeatableCreation) {
        super(conflictDueToRepeatableCreation);
    }
}

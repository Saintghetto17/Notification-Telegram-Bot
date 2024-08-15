package edu.java.bot.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LinkValidator {
    public static boolean isValidLinkURL(String link) {
        try {
            new URL(link).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}

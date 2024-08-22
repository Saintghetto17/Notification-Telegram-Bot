package edu.java.bot.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Repository
public class TrackedLinksRepository {
    private final Map<Long, List<String>> chatLinks = new HashMap<>();
    private final Map<Long, HashSet<String>> userLinks = new HashMap<>();

    public List<String> findAllLinks(Long id) {
        return chatLinks.get(id);
    }

    public boolean isChatExists(Long id) {
        return chatLinks.containsKey(id);
    }

    public void addLink(Long id, String link) {
        chatLinks.get(id).add(link);
        userLinks.get(id).add(link);
    }

    public void removeLink(Long id, String link) {

        chatLinks.get(id).remove(link);
        userLinks.get(id).remove(link);
    }

    public void create(Long id) {
        chatLinks.put(id, new ArrayList<>());
        userLinks.put(id, new HashSet<>());
    }

    public boolean isLinkExists(Long id, String link) {
        return userLinks.get(id).contains(link);
    }

}

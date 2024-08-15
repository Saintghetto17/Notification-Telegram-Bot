package edu.java.bot.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class TrackedLinksRepositoryTest {

    private TrackedLinksRepository repository = new TrackedLinksRepository();

    @SuppressWarnings("all")
    private Map<Long, List<String>> getChatLinksReflection() throws NoSuchFieldException, IllegalAccessException {
        Field privateChatLinks = TrackedLinksRepository.class.getDeclaredField("chatLinks");
        privateChatLinks.setAccessible(true);
        Map<Long, List<String>> chatLinks = (Map<Long, List<String>>) privateChatLinks.get(repository);
        return chatLinks;
    }

    @SuppressWarnings("all")
    private Map<Long, HashSet<String>> getUserLinks() throws NoSuchFieldException, IllegalAccessException {
        Field privateUserLinks = TrackedLinksRepository.class.getDeclaredField("userLinks");
        privateUserLinks.setAccessible(true);
        Map<Long, HashSet<String>> userLinks = (Map<Long, HashSet<String>>) privateUserLinks.get(repository);
        return userLinks;
    }

    @BeforeEach
    public void clean() {
        repository = new TrackedLinksRepository();
    }

    @Test
    @SuppressWarnings("all")
    public void TrackedLinksRepository_create_Test() {

        long id = 1;

        repository.create(id);

        try {

            Map<Long, List<String>> chatLinks = getChatLinksReflection();
            Map<Long, HashSet<String>> userLinks = getUserLinks();

            Assertions.assertTrue(chatLinks.containsKey(id));
            Assertions.assertTrue(userLinks.containsKey(id));
            Assertions.assertTrue(userLinks.size() == 1);
            Assertions.assertTrue(chatLinks.size() == 1);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TrackedLinksRepository_isChatExists_Test() {

        long id = 1;
        repository.create(id);
        Assertions.assertTrue(repository.isChatExists(id));
    }

    @Test
    public void TrackedLinksRepository_addLink_Test() {
        long id = 1;
        repository.create(id);
        repository.addLink(id, "https://example.com");
        try {
            Map<Long, List<String>> chatLinks = getChatLinksReflection();
            Map<Long, HashSet<String>> userLinks = getUserLinks();
            Assertions.assertTrue(chatLinks.get(id).contains("https://example.com"));
            Assertions.assertTrue(userLinks.get(id).contains("https://example.com"));
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void TrackedLinksRepository_isLinkExists_Test() {
        long id = 1;
        repository.create(id);
        repository.addLink(id, "https://example.com");

        Assertions.assertTrue(repository.isLinkExists(id, "https://example.com"));
    }

    @Test
    public void TrackedLinksRepository_removeLink_Test() {
        long id = 1;
        repository.create(id);
        repository.addLink(id, "https://example.com");
        repository.removeLink(id, "https://example.com");
        try {
            Map<Long, List<String>> chatLinks = getChatLinksReflection();
            Map<Long, HashSet<String>> userLinks = getUserLinks();
            Assertions.assertFalse(chatLinks.get(id).contains("https://example.com"));
            Assertions.assertFalse(userLinks.get(id).contains("https://example.com"));
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void TrackedLinksRepository_findAllLinks_Test() {
        long id = 1;
        repository.create(id);
        repository.addLink(id, "https://example_1.com");
        repository.addLink(id, "https://example_2.com");

        List<String> allLinks = List.of("https://example_1.com", "https://example_2.com");

        Assertions.assertEquals(allLinks, repository.findAllLinks(id));
    }
}

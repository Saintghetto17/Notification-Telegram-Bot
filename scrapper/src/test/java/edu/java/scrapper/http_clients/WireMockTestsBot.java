package edu.java.scrapper.http_clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.bot.LinkUpdate;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.dto.GithubUpdatesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@WireMockTest
public class WireMockTestsBot {

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("default_bot_url", wireMockExtension::baseUrl);
    }

    @Autowired
    private BotClient botClient;


    @Test
    public void testGetGithubUpdates() throws IOException, URISyntaxException {
        wireMockExtension.stubFor(
                WireMock.post("/updates")
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{}")));

        ResponseEntity<?> response = botClient.getBotUpdates(new LinkUpdate()
                .id(12345L)
                .url(new URI("https://example.com"))
                .description("This is a sample link description")
                .tgChatIds(Arrays.asList(67890L, 123456789L)));
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}

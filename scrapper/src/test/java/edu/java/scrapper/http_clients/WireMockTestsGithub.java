package edu.java.scrapper.http_clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.dto.GithubUpdatesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@WireMockTest
public class WireMockTestsGithub {

    private String body =
            "  {\n" +
                    "    \"id\": 1296269,\n" +
                    "    \"node_id\": \"MDEwOlJlcG9zaXRvcnkxMjk2MjY5\",\n" +
                    "    \"before\": \"6dcb09b5b57875f334f61aebed695e2e4193db5e\",\n" +
                    "    \"after\": \"827efc6d56897b048c772eb4087f854f46256132\",\n" +
                    "    \"ref\": \"refs/heads/main\",\n" +
                    "    \"pushed_at\": \"2011-01-26T19:06:43Z\",\n" +
                    "    \"push_type\": \"normal\",\n" +
                    "    \"pusher\": {\n" +
                    "      \"login\": \"octocat\",\n" +
                    "      \"id\": 1,\n" +
                    "      \"node_id\": \"MDQ6VXNlcjE=\",\n" +
                    "      \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
                    "      \"gravatar_id\": \"\",\n" +
                    "      \"url\": \"https://api.github.com/users/octocat\",\n" +
                    "      \"html_url\": \"https://github.com/octocat\",\n" +
                    "      \"followers_url\": \"https://api.github.com/users/octocat/followers\",\n" +
                    "      \"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\n" +
                    "      \"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\n" +
                    "      \"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\n" +
                    "      \"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\n" +
                    "      \"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\n" +
                    "      \"repos_url\": \"https://api.github.com/users/octocat/repos\",\n" +
                    "      \"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\n" +
                    "      \"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\n" +
                    "      \"type\": \"User\",\n" +
                    "      \"site_admin\": false\n" +
                    "    }\n" +
                    "  }";


    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("default_github_url", wireMockExtension::baseUrl);
    }

    @Autowired
    private GithubClient githubClient;


    @Test
    public void testGetGithubUpdates() throws IOException {
        wireMockExtension.stubFor(
                WireMock.get("/repos/OWNER/REPO/activity")
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody(body)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        ));
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        GithubUpdatesResponse expect = objectMapper.readValue(body, GithubUpdatesResponse.class);
        GithubUpdatesResponse actual = githubClient.getGithubUpdates("OWNER", "REPO");
        Assertions.assertEquals(expect, actual);
    }
}

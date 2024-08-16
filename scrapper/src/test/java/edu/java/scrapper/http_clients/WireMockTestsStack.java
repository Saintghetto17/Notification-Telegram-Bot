package edu.java.scrapper.http_clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.dto.QuestionUpdatesResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})

public class WireMockTestsStack {
    private String body = "[{\n" +
            "  \"tags\": [\n" +
            "    \"windows\",\n" +
            "    \"c#\",\n" +
            "    \".net\"\n" +
            "  ],\n" +
            "  \"owner\": {\n" +
            "    \"reputation\": 9001,\n" +
            "    \"user_id\": 1,\n" +
            "    \"user_type\": \"registered\",\n" +
            "    \"accept_rate\": 55,\n" +
            "    \"profile_image\": \"https://www.gravatar.com/avatar/a007be5a61f6aa8f3e85ae2fc18dd66e?d=identicon&r=PG\",\n" +
            "    \"display_name\": \"Example User\",\n" +
            "    \"link\": \"https://example.stackexchange.com/users/1/example-user\"\n" +
            "  },\n" +
            "  \"is_answered\": false,\n" +
            "  \"view_count\": 31415,\n" +
            "  \"favorite_count\": 1,\n" +
            "  \"down_vote_count\": 2,\n" +
            "  \"up_vote_count\": 3,\n" +
            "  \"answer_count\": 0,\n" +
            "  \"score\": 1,\n" +
            "  \"last_activity_date\": 1723781731,\n" +
            "  \"creation_date\": 1723738531,\n" +
            "  \"last_edit_date\": 1723806931,\n" +
            "  \"question_id\": 1234,\n" +
            "  \"link\": \"https://example.stackexchange.com/questions/1234/an-example-post-title\",\n" +
            "  \"title\": \"An example post title\",\n" +
            "  \"body\": \"An example post body\"\n" +
            "}]";

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setWireMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("default_stack_url", wireMockExtension::baseUrl);
    }

    @Autowired
    private StackOverflowClient stackOverflowClient;

    @Test
    public void testGetQuestionsUpdate() throws JsonProcessingException {
        wireMockExtension.stubFor(
                WireMock.get("/questions/1")
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody(body)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        ));
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, QuestionUpdatesResponse.class);

        List<QuestionUpdatesResponse> expect = objectMapper.readValue(body, type);
        List<QuestionUpdatesResponse> actual = stackOverflowClient.getQuestionUpdates("1");
        Assertions.assertEquals(expect, actual);
    }
}

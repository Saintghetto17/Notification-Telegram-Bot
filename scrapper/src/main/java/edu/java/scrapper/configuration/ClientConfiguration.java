package edu.java.scrapper.configuration;


import edu.java.scrapper.http_clients.BotClient;
import edu.java.scrapper.http_clients.GithubClient;
import edu.java.scrapper.http_clients.StackOverflowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.net.http.HttpClient;

@Configuration
public class ClientConfiguration {


    @Bean
    public GithubClient githubClient(@Value("${default_github_url}") String defaultGithubUrl) {
        RestClient restClient = RestClient.builder()
                .baseUrl(defaultGithubUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(GithubClient.class);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(@Value("${default_stack_url}") String defaultStackUrl) {
        RestClient restClient = RestClient.builder().baseUrl(defaultStackUrl).build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(StackOverflowClient.class);
    }

    @Bean
    public BotClient botClient(@Value("${default_bot_url}") String defaultBotUlr) {
        var client = (HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build());
        var requestFactory = new JdkClientHttpRequestFactory(client);
        RestClient restClient = RestClient.builder().
                requestFactory(requestFactory).
                baseUrl(defaultBotUlr)
                .defaultStatusHandler(new CustomResponseErrorHandler()).build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(BotClient.class);
    }
}

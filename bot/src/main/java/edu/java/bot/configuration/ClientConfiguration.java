package edu.java.bot.configuration;

import edu.java.bot.http_clients.ScrapperClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.net.http.HttpClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public ScrapperClient scrapperClient(@Value("${default_bot_scrapper}") String defaultBotScrapper) {
        var client = (HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build());
        var requestFactory = new JdkClientHttpRequestFactory(client);
        RestClient restClient = RestClient.builder()
                .requestFactory(requestFactory)
                .defaultStatusHandler(new CustomErrorResponseErrorHandler()).baseUrl(defaultBotScrapper).build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(ScrapperClient.class);
    }
}

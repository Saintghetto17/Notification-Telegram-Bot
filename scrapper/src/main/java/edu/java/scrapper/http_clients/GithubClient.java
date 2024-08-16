package edu.java.scrapper.http_clients;

import edu.java.scrapper.dto.GithubUpdatesResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GithubClient {
    @GetExchange(value = "/repos/{owner}/{repo}/activity", accept = "application/json")
    GithubUpdatesResponse getGithubUpdates(@PathVariable String owner, @PathVariable String repo);
}

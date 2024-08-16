package edu.java.scrapper.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUpdatesResponse implements Serializable {
    private long id;
    private String nodeId;
    private String before;
    private String after;
    private GithubPusher pusher;
    private OffsetDateTime pushed_at;
}

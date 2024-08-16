package edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class QuestionUpdatesResponse implements Serializable {
    private List<String> tags;
    private Owner owner;
    private boolean isAnswered;
    private long viewCount;
    private OffsetDateTime lastActivityDate;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastEditDate;
    private long questionId;
    private String link;
    private String title;
    private String body;
}

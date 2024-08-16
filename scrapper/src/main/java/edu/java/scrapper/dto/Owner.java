package edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class Owner implements Serializable {
    private long reputation;
    private long user_id;
    private String user_type;
    private String accept_rate;
    private String link;
}

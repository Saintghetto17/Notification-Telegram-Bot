package com.example.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddLinkRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-22T13:29:06.099782300+03:00[Europe/Moscow]")
public class AddLinkRequest {

  private URI link;

  public AddLinkRequest link(URI link) {
    this.link = link;
    return this;
  }

  /**
   * Get link
   * @return link
  */
  @Valid 
  @Schema(name = "link", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("link")
  public URI getLink() {
    return link;
  }

  public void setLink(URI link) {
    this.link = link;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddLinkRequest addLinkRequest = (AddLinkRequest) o;
    return Objects.equals(this.link, addLinkRequest.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddLinkRequest {\n");
    sb.append("    link: ").append(toIndentedString(link)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


package com.example.model;

import java.net.URI;
import java.util.Objects;
import com.example.model.LinkResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ListLinksResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-22T13:29:06.099782300+03:00[Europe/Moscow]")
public class ListLinksResponse {

  @Valid
  private List<@Valid LinkResponse> links;

  private Integer size;

  public ListLinksResponse links(List<@Valid LinkResponse> links) {
    this.links = links;
    return this;
  }

  public ListLinksResponse addLinksItem(LinkResponse linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<>();
    }
    this.links.add(linksItem);
    return this;
  }

  /**
   * Get links
   * @return links
  */
  @Valid 
  @Schema(name = "links", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("links")
  public List<@Valid LinkResponse> getLinks() {
    return links;
  }

  public void setLinks(List<@Valid LinkResponse> links) {
    this.links = links;
  }

  public ListLinksResponse size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
  */
  
  @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListLinksResponse listLinksResponse = (ListLinksResponse) o;
    return Objects.equals(this.links, listLinksResponse.links) &&
        Objects.equals(this.size, listLinksResponse.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListLinksResponse {\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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


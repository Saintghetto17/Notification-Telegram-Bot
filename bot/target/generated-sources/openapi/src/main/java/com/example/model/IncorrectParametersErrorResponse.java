package com.example.model;

import java.net.URI;
import java.util.Objects;
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
 * IncorrectParametersErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-22T11:42:38.161026300+03:00[Europe/Moscow]")
public class IncorrectParametersErrorResponse {

  private String description;

  private String code;

  private String exceptionName;

  private String exceptionMessage;

  @Valid
  private List<String> stacktrace;

  public IncorrectParametersErrorResponse description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public IncorrectParametersErrorResponse code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
  */
  
  @Schema(name = "code", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public IncorrectParametersErrorResponse exceptionName(String exceptionName) {
    this.exceptionName = exceptionName;
    return this;
  }

  /**
   * Get exceptionName
   * @return exceptionName
  */
  
  @Schema(name = "exceptionName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exceptionName")
  public String getExceptionName() {
    return exceptionName;
  }

  public void setExceptionName(String exceptionName) {
    this.exceptionName = exceptionName;
  }

  public IncorrectParametersErrorResponse exceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
    return this;
  }

  /**
   * Get exceptionMessage
   * @return exceptionMessage
  */
  
  @Schema(name = "exceptionMessage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exceptionMessage")
  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public IncorrectParametersErrorResponse stacktrace(List<String> stacktrace) {
    this.stacktrace = stacktrace;
    return this;
  }

  public IncorrectParametersErrorResponse addStacktraceItem(String stacktraceItem) {
    if (this.stacktrace == null) {
      this.stacktrace = new ArrayList<>();
    }
    this.stacktrace.add(stacktraceItem);
    return this;
  }

  /**
   * Get stacktrace
   * @return stacktrace
  */
  
  @Schema(name = "stacktrace", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stacktrace")
  public List<String> getStacktrace() {
    return stacktrace;
  }

  public void setStacktrace(List<String> stacktrace) {
    this.stacktrace = stacktrace;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncorrectParametersErrorResponse incorrectParametersErrorResponse = (IncorrectParametersErrorResponse) o;
    return Objects.equals(this.description, incorrectParametersErrorResponse.description) &&
        Objects.equals(this.code, incorrectParametersErrorResponse.code) &&
        Objects.equals(this.exceptionName, incorrectParametersErrorResponse.exceptionName) &&
        Objects.equals(this.exceptionMessage, incorrectParametersErrorResponse.exceptionMessage) &&
        Objects.equals(this.stacktrace, incorrectParametersErrorResponse.stacktrace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, code, exceptionName, exceptionMessage, stacktrace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncorrectParametersErrorResponse {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    exceptionName: ").append(toIndentedString(exceptionName)).append("\n");
    sb.append("    exceptionMessage: ").append(toIndentedString(exceptionMessage)).append("\n");
    sb.append("    stacktrace: ").append(toIndentedString(stacktrace)).append("\n");
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


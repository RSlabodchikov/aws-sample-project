package com.slabodchykau.aws.lambda.events.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDetails {

  private final String message;
  private final Integer statusCode;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    if (message != null) {
      sb.append("message: ").append(message).append(",");
    }
    if (statusCode != null) {
      sb.append("status: ").append(statusCode);
    }
    sb.append("}");
    return sb.toString();
  }
}

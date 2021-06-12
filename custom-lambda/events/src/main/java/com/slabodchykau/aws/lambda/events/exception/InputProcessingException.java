package com.slabodchykau.aws.lambda.events.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InputProcessingException extends RuntimeException {
  
  private ErrorDetails details;
  public static final String ERROR_MESSAGE = "Unable to process input data for request %s. Error message: Error: %s";
  
  public InputProcessingException(ErrorDetails details) {
    super();
    this.details = details;
  }
}

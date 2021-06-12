package com.slabodchykau.aws.lambda.events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.slabodchykau.aws.lambda.events.exception.ErrorDetails;
import com.slabodchykau.aws.lambda.events.exception.InputProcessingException;
import com.slabodchykau.aws.lambda.events.exception.NotImplementedException;

/**
 * Handler for input data of various event types.
 * Separate handler class should be defined for every aws event.
 */
public abstract class EventHandler<I, O> implements RequestHandler<I, O> {

  /**
   * Base method for handling input data for different aws events.
   *
   * @param inputData input
   * @param context   lambda runtime context
   * @return processed data. By default its not changed inputData
   * @throws InputProcessingException if inputData not specified
   */
  protected String handle(String inputData, Context context) {
    if (inputData == null) {
      throw new InputProcessingException(ErrorDetails.builder()
          .message(String.format("Unable to process empty input data for request: %s.", context.getAwsRequestId()))
          .statusCode(500)
          .build());
    }
    context.getLogger().log(String.format("Received input for request: %s.\n" +
        "Input: %s.", context.getAwsRequestId(), inputData));
    return inputData;
  }

  @Override
  public O handleRequest(I i, Context context) {
    throw new NotImplementedException();
  }
}

package com.slabodchykau.aws.lambda.events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.slabodchykau.aws.lambda.events.exception.InputProcessingException;

import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class APIGatewayEventHandler extends EventHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


  private final Map<String, String> headers = Map.of(
      "Content-Type", "application/json",
      "X-Custom-Header", "application/json");

  @Override
  public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {

    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
        .withHeaders(headers);
    try {
      String result = handle(input.getBody(), context);
      return response
          .withBody(result)
          .withStatusCode(200);
    } catch (InputProcessingException e) {
      context.getLogger().log(e.getDetails().getMessage());
      return response
          .withBody(e.getDetails().getMessage())
          .withStatusCode(e.getDetails().getStatusCode());
    }
  }
}

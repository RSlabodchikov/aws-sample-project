package com.slabodchykau.aws.lambda.events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.CognitoEvent;

public class CognitoEventHandler extends EventHandler<CognitoEvent, String> {

  @Override
  public String handleRequest(CognitoEvent cognitoEvent, Context context) {
    handle(cognitoEvent.toString(), context);
    return "200 OK";
  }
}

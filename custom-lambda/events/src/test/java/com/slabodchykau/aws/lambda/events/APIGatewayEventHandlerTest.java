package com.slabodchykau.aws.lambda.events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class APIGatewayEventHandlerTest {

  @Mock
  private Context context;
  private static final LambdaLogger logger = new LambdaLogger() {
    public void log(String message) {
      System.out.print(message);
    }

    public void log(byte[] message) {
      try {
        System.out.write(message);
      } catch (IOException var3) {
        var3.printStackTrace();
      }

    }
  };

  @Test
  public void emptyAPIGatewayEventTest() {
    APIGatewayEventHandler APIGatewayEventHandler = new APIGatewayEventHandler();
    when(context.getAwsRequestId()).thenReturn("10");
    when(context.getLogger()).thenReturn(logger);
    
    APIGatewayProxyResponseEvent result = APIGatewayEventHandler.handleRequest(new APIGatewayProxyRequestEvent(), context);

    assertEquals(result.getStatusCode().intValue(), 500);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains(context.getAwsRequestId()));
  }

  @Test
  @SneakyThrows
  public void validAPIGatewayEventTest() {
    APIGatewayEventHandler APIGatewayEventHandler = new APIGatewayEventHandler();
    String body = (new ObjectMapper()).writeValueAsString(Map.of("property", "value"));
    APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent();
    event.setBody(body);
    when(context.getAwsRequestId()).thenReturn("10");
    when(context.getLogger()).thenReturn(logger);

    APIGatewayProxyResponseEvent result = APIGatewayEventHandler.handleRequest(event, context);

    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("property"));
    assertTrue(content.contains("value"));
  }
}

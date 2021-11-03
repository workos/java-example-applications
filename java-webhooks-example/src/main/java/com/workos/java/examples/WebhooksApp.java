package com.workos.java.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.webhooks.models.Webhook;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.websocket.WsContext;
import java.security.SignatureException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebhooksApp {
  private static Integer wsSessionId = 1;
  private static Map<WsContext, Integer> webSocketSessions = new ConcurrentHashMap<>();

  private Javalin app;

  private WorkOS workos;

  private ObjectMapper mapper = new ObjectMapper();

  private String webhookSecret;

  public WebhooksApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create()
      .ws("/webhooks-ws", ws -> {
        ws.onConnect(ctx -> {
          webSocketSessions.put(ctx, wsSessionId += 1);
        });
        ws.onClose(ctx -> {
          webSocketSessions.remove(ctx);
        });
      })
      .start(7005);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    webhookSecret = env.get("WORKOS_WEBHOOK_SECRET");

    app.get("/", ctx -> ctx.render("home.jte"));
    app.post("/webhooks", this::webhooks);
  }

  public Context webhooks(Context ctx) {
    String payload = ctx.body();
    System.out.println(ctx.body());
    System.out.println(ctx.req.getHeaderNames());
    try {
      Webhook wh = workos.webhooks.constructEvent(
        payload,
        ctx.header("WorkOS-Signature"),
        webhookSecret
      );
      String webhookJson =  mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(wh);

      this.broadcastWebhookReceived(webhookJson);
    } catch (SignatureException e) {
      System.err.println("Webhook error: " + e.getMessage());
      return ctx.status(400);
    } catch (JsonProcessingException e) {
      System.err.println("Error parsing webhook json");
      return ctx.status(422);
    }
    return ctx.status(200);
  }

  public void broadcastWebhookReceived(String webhookJson) {
    webSocketSessions.keySet().stream().filter(ctx -> ctx.session.isOpen())
      .forEach(session -> {
        session.send(webhookJson);
      });
  }

  public static void main(String[] args) {
    new WebhooksApp();
  }
}

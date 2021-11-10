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
  private static final Map<WsContext, Integer> webSocketSessions = new ConcurrentHashMap<>();

  private final WorkOS workos;

  private final ObjectMapper mapper = new ObjectMapper();

  private final String webhookSecret;

  public WebhooksApp() {
    Map<String, String> env = System.getenv();

    Javalin app = Javalin.create()
      .ws("/webhooks-ws", ws -> {
        ws.onConnect(ctx -> webSocketSessions.put(ctx, wsSessionId += 1));
        ws.onClose(webSocketSessions::remove);
      })
      .start(7005);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    webhookSecret = env.get("WORKOS_WEBHOOK_SECRET");

    if (webhookSecret == null || webhookSecret.isEmpty()) {
      throw new IllegalArgumentException(
        "You must export an environment variable with WORKOS_WEBHOOK_SECRET");
    }

    app.get("/", ctx -> ctx.render("home.jte"));
    app.post("/webhooks", this::webhooks);
  }

  public void webhooks(Context ctx) {
    String payload = ctx.body();
    System.out.println(ctx.body());
    System.out.println(ctx.req.getHeaderNames());
    try {
      String signatureHeader = ctx.header("WorkOS-Signature");
      if (signatureHeader == null) {
        signatureHeader = "";
      }
      Webhook wh = workos.webhooks.constructEvent(
        payload,
        signatureHeader,
        webhookSecret,
        360
      );
      String webhookJson =  mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(wh);

      this.broadcastWebhookReceived(webhookJson);
    } catch (SignatureException e) {
      System.err.println("Webhook error: " + e.getMessage());
      ctx.status(400);
      return;
    } catch (JsonProcessingException e) {
      System.err.println("Error parsing webhook json");
      ctx.status(422);
      return;
    }
    ctx.status(200);
  }

  public void broadcastWebhookReceived(String webhookJson) {
    webSocketSessions.keySet().stream().filter(ctx -> ctx.session.isOpen())
      .forEach(session -> session.send(webhookJson));
  }

  public static void main(String[] args) {
    new WebhooksApp();
  }
}

package com.workos.java.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.webhooks.models.DirectoryActivatedEvent;
import com.workos.webhooks.models.DirectoryDeactivatedEvent;
import com.workos.webhooks.models.DirectoryDeletedEvent;
import com.workos.webhooks.models.DirectoryGroupCreatedEvent;
import com.workos.webhooks.models.DirectoryGroupDeletedEvent;
import com.workos.webhooks.models.DirectoryGroupUpdatedEvent;
import com.workos.webhooks.models.DirectoryGroupUserAddedEvent;
import com.workos.webhooks.models.DirectoryGroupUserRemovedEvent;
import com.workos.webhooks.models.DirectoryUserCreatedEvent;
import com.workos.webhooks.models.DirectoryUserDeletedEvent;
import com.workos.webhooks.models.DirectoryUserUpdatedEvent;
import com.workos.webhooks.models.WebhookEvent;
import io.github.cdimascio.dotenv.Dotenv;
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
    Dotenv env = Dotenv.configure().directory("../.env").load();

    Javalin app =
        Javalin.create()
            .ws(
                "/webhooks-ws",
                ws -> {
                  ws.onConnect(ctx -> webSocketSessions.put(ctx, wsSessionId += 1));
                  ws.onClose(webSocketSessions::remove);
                })
            .start(7005);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    webhookSecret = env.get("WORKOS_WEBHOOK_SECRET");

    if (webhookSecret == null || webhookSecret.isEmpty()) {
      throw new IllegalArgumentException(
          "You must add the WORKOS_WEBHOOK_SECRET environment variable to the .env file");
    }

    app.get("/", ctx -> ctx.render("home.jte"));
    app.post("/webhooks", this::webhooksHandler);
  }

  private void webhooksHandler(Context ctx) {
    String payload = ctx.body();

    try {
      String signatureHeader = ctx.header("WorkOS-Signature");
      if (signatureHeader == null) {
        signatureHeader = "";
      }
      WebhookEvent wh = workos.webhooks.constructEvent(payload, signatureHeader, webhookSecret, 3000);
      String webhookJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wh);

      this.logWebhook(wh);

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

  private void broadcastWebhookReceived(String webhookJson) {
    webSocketSessions.keySet().stream()
        .filter(ctx -> ctx.session.isOpen())
        .forEach(session -> session.send(webhookJson));
  }

  private void logWebhook(WebhookEvent we) {
    if (we instanceof DirectoryActivatedEvent) {
      System.out.println(we.event + " Directory: " + ((DirectoryActivatedEvent) we).data.name + " activated.");
    }

    if (we instanceof DirectoryDeactivatedEvent) {
      System.out.println(we.event + " Directory: " + ((DirectoryDeactivatedEvent) we).data.name + " deactivated.");
    }

    if (we instanceof DirectoryDeletedEvent) {
      System.out.println(we.event + " Directory: " + ((DirectoryDeactivatedEvent) we).data.name + " deleted.");
    }

    if (we instanceof DirectoryUserCreatedEvent) {
      DirectoryUserCreatedEvent event = (DirectoryUserCreatedEvent) we;
      System.out.println(we.event + " Directory User: " + event.data.firstName + " "
        + event.data.lastName + " created");
    }

    if (we instanceof DirectoryUserDeletedEvent) {
      DirectoryUserDeletedEvent event = (DirectoryUserDeletedEvent) we;
      System.out.println(we.event + " Directory User: " + event.data.firstName + " "
        + event.data.lastName + " deleted");
    }

    if (we instanceof DirectoryUserUpdatedEvent) {
      DirectoryUserUpdatedEvent event = (DirectoryUserUpdatedEvent) we;
      System.out.println(we.event + " Directory User: " + event.data.firstName + " "
        + event.data.lastName + " updated");
    }

    if (we instanceof DirectoryGroupCreatedEvent) {
      DirectoryGroupCreatedEvent event = (DirectoryGroupCreatedEvent) we;
      System.out.println(we.event + " Directory Group: " + event.data.name + " created");
    }

    if (we instanceof DirectoryGroupDeletedEvent) {
      DirectoryGroupDeletedEvent event = (DirectoryGroupDeletedEvent) we;
      System.out.println(we.event + " Directory Group: " + event.data.name + " deleted");
    }

    if (we instanceof DirectoryGroupUpdatedEvent) {
      DirectoryGroupUpdatedEvent event = (DirectoryGroupUpdatedEvent) we;
      System.out.println(we.event + " Directory Group: " + event.data.name + " updated");
    }

    if (we instanceof DirectoryGroupUserAddedEvent) {
      DirectoryGroupUserAddedEvent event = (DirectoryGroupUserAddedEvent) we;
      System.out.println(we.event + " User: " + event.data.user.id + " added to Group: "
        + event.data.group.id + " in Directory: " + event.data.directoryId);
    }

    if (we instanceof DirectoryGroupUserRemovedEvent) {
      DirectoryGroupUserRemovedEvent event = (DirectoryGroupUserRemovedEvent) we;
      System.out.println(we.event + " User: " + event.data.user.id + " removed from Group: "
        + event.data.group.id + " in Directory: " + event.data.directoryId);
    }
  }

  public static void main(String[] args) {
    new WebhooksApp();
  }
}

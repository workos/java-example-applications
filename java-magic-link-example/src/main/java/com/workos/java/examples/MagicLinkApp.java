package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.passwordless.PasswordlessApi.CreateSessionOptions;
import com.workos.passwordless.models.PasswordlessSession;
import com.workos.sso.models.ProfileAndToken;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MagicLinkApp {

  private final WorkOS workos;

  private final String redirectUrl = "/callback";

  private final String clientId;

  public MagicLinkApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();
    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    clientId = env.get("WORKOS_CLIENT_ID");

    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalArgumentException(
          "`WORKOS_CLIENT_ID` environment variable must be set. You can retrieve this from https://dashboard.workos.com/configuration");
    }

    Javalin app = Javalin.create(config -> {
      config.addStaticFiles("src/resources", Location.EXTERNAL);
    }).start(7001);

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get(redirectUrl, this::callback);
    app.post("/send-magic-link", this::sendMagicLink);
  }

  private void sendMagicLink(Context ctx) {
    try {
      String email = ctx.formParam("email");

      assert email != null;
      CreateSessionOptions options =
          CreateSessionOptions.builder()
              .email(email)
              .redirectUri("http://localhost:7001" + redirectUrl)
              .state("myCustomApplicationState")
              .build();

      PasswordlessSession session = workos.passwordless.createSession(options);

      // optionally you can send the e-mail yourself using the url from `session.link`

      workos.passwordless.sendSession(session.id);
      ctx.render("sent.jte", Collections.singletonMap("email", email));
    } catch (Exception e) {
      e.printStackTrace();
      String errorMessage = e.getMessage();
      ctx.render("error.jte", Collections.singletonMap("errorMessage", errorMessage));
    }
  }

  private void callback(Context ctx) {
    String code = ctx.queryParam("code");
    String errorMessage = ctx.queryParam("error_description");
    String state = ctx.queryParam("state");

    if (code == null || code.isBlank()) {
      ctx.render("error.jte", Collections.singletonMap("errorMessage", errorMessage));
    } else {
      ProfileAndToken profileAndToken = workos.sso.getProfileAndToken(code, clientId);
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("profile", profileAndToken.profile);
      if (state != null) {
        jteParams.put("state", state);
      }

      ctx.render("profile.jte", jteParams);
    }
  }

  public static void main(String[] args) {
    new MagicLinkApp();
  }
}

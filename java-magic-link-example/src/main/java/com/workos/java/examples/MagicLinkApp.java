package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.common.exceptions.UnprocessableEntityException;
import com.workos.common.http.UnprocessableEntityExceptionResponse.EntityError;
import com.workos.passwordless.PasswordlessApi.CreateSessionOptions;
import com.workos.passwordless.models.PasswordlessSession;
import com.workos.sso.models.ProfileAndToken;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MagicLinkApp {

  private final WorkOS workos;

  private final String redirectUrl = "/callback";

  private String clientId;

  public MagicLinkApp() {
    Map<String, String> env = System.getenv();
    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    clientId = env.get("WORKOS_CLIENT_ID");

    if (clientId == null || clientId.isEmpty()) {
      throw new IllegalArgumentException("`WORKOS_CLIENT_ID` environment variable must be set. You can retrieve this from https://dashboard.workos.com/configuration");
    }

    Javalin app = Javalin.create().start(7004);

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get(redirectUrl, this::callback);
    app.post("/send-magic-link", this::sendMagicLink);
  }

  private void sendMagicLink(Context ctx) {
    try {
      String email = ctx.formParam("email");

      CreateSessionOptions options = CreateSessionOptions.builder()
        .email(email)
        .redirectUri("http://localhost:7004" + redirectUrl)
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

  private Context callback(Context ctx) {
    String code = ctx.queryParam("code");

    ProfileAndToken profileAndToken = workos.sso.getProfileAndToken(code, clientId);

    return ctx.render("profile.jte", Collections.singletonMap("profile", profileAndToken.profile));
  }


  public static void main(String[] args) {
    new MagicLinkApp();
  }
}

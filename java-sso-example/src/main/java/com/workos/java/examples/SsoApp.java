package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.sso.models.ProfileAndToken;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;
import java.util.Collections;

public class SsoApp {
  private Javalin app;

  private WorkOS workos;

  private String clientId;

  public SsoApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create().start(7000);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    clientId = env.get("WORKOS_CLIENT_ID");

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get("/login", this::login);
    app.get("/callback", this::callback);
  }

  public void login(Context ctx) {
    String url =
        workos
            .sso
            .getAuthorizationUrl(clientId, "http://localhost:7000/callback")
            .domain("gmail.com")
            .build();

    ctx.redirect(url);
  }

  public Context callback(Context ctx) {
    String code = ctx.queryParam("code");

    ProfileAndToken profileAndToken = workos.sso.getProfileAndToken(code, clientId);

    return ctx.render("profile.jte", Collections.singletonMap("profile", profileAndToken.getProfile()));
  }

  public static void main(String[] args) {
    new SsoApp();
  }
}

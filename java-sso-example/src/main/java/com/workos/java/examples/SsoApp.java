package com.workos.java.examples;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.workos.WorkOS;
import com.workos.sso.models.ProfileAndToken;

import java.util.Map;

public class SsoApp {
  private Javalin app;

  private WorkOS workos;

  private String clientId;

  public SsoApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create().start(7000);

    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    clientId = env.get("WORKOS_CLIENT_ID");

    System.out.println("Java SSO Example Application running: http://localhost:7000");

    app.get("/login", ctx -> this.login(ctx));
    app.get("/callback", ctx -> this.callback(ctx));
  }

  public void login(Context ctx) {
    String url = workos.getSso()
      .getAuthorizationUrl(clientId, "http://localhost:7000/callback")
      .domain("gmail.com")
      .build();

    ctx.redirect(url);
  }

  public Context callback(Context ctx) {
    String code = ctx.queryParam("code");

    ProfileAndToken profile = workos.getSso().getProfileAndToken(code, clientId);

    return ctx.result(profile.toString());
  }

  public static void main(String[] args) {
    new SsoApp();
  }
}

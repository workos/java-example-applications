package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.sso.models.ProfileAndToken;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.Collections;

public class SsoApp {
  private final WorkOS workos;

  private final String clientId;

  public SsoApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();

    Javalin app = Javalin.create(config -> {
      config.addStaticFiles("src/resources", Location.EXTERNAL);
    }).start(7004);

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
            .getAuthorizationUrl(clientId, "http://localhost:7004/callback")
            .domain("gmail.com")
            .build();

    ctx.redirect(url);
  }

  public void callback(Context ctx) {
    String code = ctx.queryParam("code");

    assert code != null;
    ProfileAndToken profileAndToken = workos.sso.getProfileAndToken(code, clientId);

    ctx.render("profile.jte", Collections.singletonMap("profile", profileAndToken.profile));
  }

  public static void main(String[] args) {
    new SsoApp();
  }
}

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
    }).start(7001);

    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    clientId = env.get("WORKOS_CLIENT_ID");

    app.get("/", this::isLoggedIn);
    app.post("/login", this::login);
    app.get("/callback", this::callback);
    app.get("logout", this::logout);
  }

  public void login(Context ctx) {
    Dotenv env = Dotenv.configure().directory("../.env").load();
    String organizationId = env.get("WORKOS_ORGANIZATION_ID");
    String loginType = ctx.formParam("login_method");

    if (loginType.equals("saml")) {
      String url =
        workos
          .sso
          .getAuthorizationUrl(clientId, "http://localhost:7001/callback")
          .organization(organizationId)
          .build();
      ctx.redirect(url);
    } else {
      String url =
        workos
          .sso
          .getAuthorizationUrl(clientId, "http://localhost:7001/callback")
          .provider(loginType)
          .build();
      ctx.redirect(url);
    }
  }

  public void callback(Context ctx) {
    String code = ctx.queryParam("code");

    assert code != null;
    ProfileAndToken profileAndToken = workos.sso.getProfileAndToken(code, clientId);
    ctx.sessionAttribute("profile", profileAndToken.profile);

    ctx.redirect("/");
  }

  public void isLoggedIn(Context ctx) {

    if (ctx.sessionAttribute("profile") != null){
      ctx.render("profile.jte", Collections.singletonMap("profile", ctx.sessionAttribute("profile")));
    } else {
      ctx.render("home.jte");
    }
  }

  public void logout(Context ctx ) {
    ctx.sessionAttribute("profile", null);
    ctx.redirect("/");
  }

  public static void main(String[] args) {
    new SsoApp();
  }
}

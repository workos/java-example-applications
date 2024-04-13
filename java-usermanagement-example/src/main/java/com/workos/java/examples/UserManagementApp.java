package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.usermanagement.models.Identity;
import com.workos.usermanagement.models.User;
import com.workos.usermanagement.types.UserManagementProviderEnumType;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.Map;


public class UserManagementApp {
  private final WorkOS workos;
  private final String clientId;

  public UserManagementApp() {
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
    String loginMethod = ctx.formParam("login_method");

    String url =
      workos
        .userManagement
        .getAuthorizationUrl(clientId, "http://localhost:7001/callback")
        .provider(UserManagementProviderEnumType.valueOf(loginMethod))
        .build();

    ctx.redirect(url);
  }

  public void callback(Context ctx) {
    String code = ctx.queryParam("code");

    assert code != null;

    User user = workos.userManagement.authenticateWithCode(clientId, code, null).getUser();
    Identity[] identities = workos.userManagement.getUserIdentities(user.getId());
    ctx.sessionAttribute("user", user);
    ctx.sessionAttribute("identities", identities);

    ctx.redirect("/");
  }

  public void isLoggedIn(Context ctx) {
    if (ctx.sessionAttribute("user") != null){
      ctx.render("user.jte", Map.of("user", ctx.sessionAttribute("user"), "identities", ctx.sessionAttribute("identities")));
    } else {
      ctx.render("home.jte");
    }
  }

  public void logout(Context ctx ) {
    ctx.sessionAttribute("user", null);
    ctx.redirect("/");
  }

  public static void main(String[] args) {
    new UserManagementApp();
  }
}

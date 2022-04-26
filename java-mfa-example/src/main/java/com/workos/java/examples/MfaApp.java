package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.mfa.MfaApi;
import com.workos.mfa.MfaApi.EnrollFactorOptions;
import com.workos.mfa.models.Factor;

import com.workos.sso.models.ProfileAndToken;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MfaApp {
  private final WorkOS workos;
  private final String clientId;

  public MfaApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();

    Javalin app = Javalin.create(config -> {
      config.addStaticFiles("src/resources", Location.EXTERNAL);
    }).start(7001);

    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    clientId = env.get("WORKOS_CLIENT_ID");

    app.get("/", this::isLoggedIn);
    app.get("/login", this::login);
    app.get("/callback", this::callback);
    app.get("logout", this::logout);
    app.post("/enroll_factor", this::enroll_factor);
  }

  public void enroll_factor(Context ctx) {
    String phoneNumber = ctx.formParam("phone_number");
    EnrollFactorOptions options = MfaApi.EnrollFactorOptions.builder()
      .type("sms")
      .phoneNumber(phoneNumber)
      .build();
    Factor factor = workos.mfa.enrollFactor(options);

    if(ctx.sessionAttribute("factorList") != null) {
      System.out.print("hit not-null part");
      Map<String, Factor> factorList = ctx.sessionAttribute("factorList");
      factorList.put(factor.id, factor);
      ctx.sessionAttribute("factorList", factorList);
    } else {
      System.out.print("hit null part");
      Map<String, Factor> factorList = new HashMap<>();
      factorList.put(factor.id, factor);

      ctx.sessionAttribute("factorList", factorList);
    }

    Map<String, Factor> factorList = ctx.sessionAttribute("factorList");

    System.out.println("factorlist before render");
    System.out.println(factorList);

    ctx.render("home.jte", factorList);
  }

  public void login(Context ctx) {
    Dotenv env = Dotenv.configure().directory("../.env").load();
    String connectionId = env.get("WORKOS_CONNECTION_ID");
    String url =
        workos
            .sso
            .getAuthorizationUrl(clientId, "http://localhost:7001/callback")
            .connection(connectionId)
            .build();

    ctx.redirect(url);
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
    new MfaApp();
  }
}

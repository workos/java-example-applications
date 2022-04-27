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
<<<<<<< HEAD
import com.workos.mfa.MfaApi;
import com.workos.mfa.MfaApi.EnrollFactorOptions;
import com.workos.mfa.models.Factor;
=======
>>>>>>> a88d3ab782a7475ee5659b72a239284612f85c15


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

<<<<<<< HEAD
    app.get("/", ctx -> ctx.render("home.jte"));
=======
    app.get("/", this::isLoggedIn);
    app.get("/login", this::login);
    app.get("/callback", this::callback);
    app.get("logout", this::logout);
>>>>>>> a88d3ab782a7475ee5659b72a239284612f85c15
    app.post("/enroll_factor", this::enroll_factor);
  }

  public void enroll_factor(Context ctx) {
<<<<<<< HEAD
    System.out.println("hit the method");
    String sms = ctx.queryParam("sms");
    String totp = ctx.queryParam("totp");

=======
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
>>>>>>> a88d3ab782a7475ee5659b72a239284612f85c15


    EnrollFactorOptions options = MfaApi.EnrollFactorOptions.builder()
      .type("sms")
      .phoneNumber("3609290957")
      .build();



    Factor factor = workos.mfa.enrollFactor(options);
    System.out.print(factor);

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("factors", factor);

    System.out.println(jteParams);

    ctx.redirect("/");
  }


  public static void main(String[] args) {
    new MfaApp();
  }
}

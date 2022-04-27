package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.sso.models.ProfileAndToken;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.workos.mfa.MfaApi;
import com.workos.mfa.MfaApi.EnrollFactorOptions;
import com.workos.mfa.models.Factor;


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

    app.get("/", ctx -> ctx.render("home.jte"));
    app.post("/enroll_factor", this::enroll_factor);
  }


  public void enroll_factor(Context ctx) {
    System.out.println("hit the method");
    String sms = ctx.queryParam("sms");
    String totp = ctx.queryParam("totp");



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

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
import java.lang.reflect.Array;
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

    app.get("/", this::home);
    app.get("clear_session", this::clear_session);
    app.get("factor_detail", this::factor_detail);
    app.post("/enroll_factor", this::enroll_factor);
    app.get("/enroll_factor_details", this::enroll_factor_details);
  }


  public void home(Context ctx) {
    if(ctx.sessionAttribute("arrayFactorList") != null) {

      System.out.println(ctx.sessionAttributeMap());
      ArrayList<Object> factorList = ctx.sessionAttribute("arrayFactorList");
      System.out.println(factorList);
      ctx.render("home.jte", Collections.singletonMap("factorList", factorList));
    } else {
      ctx.render("home.jte");
    }
  }

  public void factor_detail(Context ctx) {
    ctx.render("factor_detail.jte");
  }

  public void enroll_factor_details(Context ctx ) {
    ctx.render("enroll_factor.jte");
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
      Map<String, Object> factorList = ctx.sessionAttribute("factorList");
      factorList.put(String.valueOf(factorList.size()), factor);
      ctx.sessionAttribute("factorList", factorList);
    } else {
      System.out.print("hit null part");
      Map<String, Object> factorList = new HashMap<>();
      factorList.put(String.valueOf(factorList.size()), factor);
      ctx.sessionAttribute("factorList", factorList);
    }

    Map<String, Factor> factorList = ctx.sessionAttribute("factorList");
    ArrayList<Object> list = new ArrayList<Object>(factorList.values());
    ctx.sessionAttribute("arrayFactorList", list);

    System.out.println("arrayFactorList before render");
    System.out.println(list);

    ctx.redirect("/");
//    ctx.render("home.jte", Collections.singletonMap("factorList", list));
  }


  public void clear_session(Context ctx ) {
    ctx.sessionAttribute("factorList", null);
    ctx.sessionAttribute("arrayFactorList", null);
    ctx.redirect("/");
  }

  public static void main(String[] args) {
    new MfaApp();
  }
}

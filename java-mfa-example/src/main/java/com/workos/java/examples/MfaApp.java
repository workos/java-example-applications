package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.mfa.MfaApi;
import com.workos.mfa.MfaApi.EnrollFactorOptions;
import com.workos.mfa.MfaApi.ChallengeFactorOptions;
import com.workos.mfa.models.Challenge;
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
    app.post("/challenge_factor", this::challenge_factor);
  }


  public void home(Context ctx) {
    if(ctx.sessionAttribute("arrayFactorList") != null) {
      ArrayList<String> factorIdList = ctx.sessionAttribute("factorIdList");
      ArrayList<Object> factorList = ctx.sessionAttribute("arrayFactorList");
      ctx.render("home.jte", Collections.singletonMap("factorIdList", factorIdList));
    } else {
      ctx.render("home.jte");
    }
  }

  public void challenge_factor(Context ctx) {
    String smsCode = ctx.formParam("sms_message");
    String currentFactorId = ctx.sessionAttribute("currentFactorId");
    System.out.println(smsCode);
    System.out.println(currentFactorId);

    ChallengeFactorOptions options = MfaApi.ChallengeFactorOptions.builder()
      .authenticationFactorId(currentFactorId)
      .smsTemplate(smsCode)
      .build();
    System.out.println(options);

    Challenge challenge = workos.mfa.challengeFactor(options);
    System.out.println(challenge);

    ctx.render ("challenge_factor.jte");
  }

  public void factor_detail(Context ctx) {
    String factorId = ctx.queryParam("id");
    HashMap<String, Factor> currentFactors = ctx.sessionAttribute("factorList");
    Factor currentFactor = currentFactors.get(factorId);
    ctx.sessionAttribute("currentFactorId", factorId);

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("factorId", currentFactor.id);
    jteParams.put("createdAt", currentFactor.createdAt);
    jteParams.put("type", currentFactor.type);
    jteParams.put("phoneNumber", currentFactor.sms.phoneNumber);

    ctx.render("factor_detail.jte", jteParams);
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
    String factorId = factor.id;

    if(ctx.sessionAttribute("factorList") != null) {
      ArrayList<String> factorIdList = ctx.sessionAttribute("factorIdList");
      factorIdList.add(factorId);
      ctx.sessionAttribute("factorIdList", factorIdList);

      HashMap<String, Factor> factorList = ctx.sessionAttribute("factorList");
      factorList.put(factorId, factor);
      ctx.sessionAttribute("factorList", factorList);
    } else {
      ArrayList<String> factorIdList = new ArrayList<>();
      factorIdList.add(factorId);
      ctx.sessionAttribute("factorIdList", factorIdList);

      HashMap<String, Factor> factorList = new HashMap<>();
      factorList.put(factorId, factor);
      ctx.sessionAttribute("factorList", factorList);
    }

    HashMap<String, Factor> factorList = ctx.sessionAttribute("factorList");
    ArrayList<Object> list = new ArrayList<Object>(factorList.values());
    ctx.sessionAttribute("arrayFactorList", list);

    ctx.redirect("/");
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

package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.mfa.MfaApi;
import com.workos.mfa.MfaApi.EnrollFactorOptions;
import com.workos.mfa.MfaApi.ChallengeFactorOptions;
import com.workos.mfa.MfaApi.VerifyFactorOptions;
import com.workos.mfa.models.Challenge;
import com.workos.mfa.models.Factor;
import com.workos.mfa.models.VerifyFactorResponse;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;


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
    app.get("/clear_session", this::clear_session);
    app.get("/factor_detail", this::factor_detail);
    app.post("/enroll_sms_factor", this::enroll_sms_factor);
    app.post("/enroll_totp_factor", this::enroll_totp_factor);
    app.get("/enroll_factor_details", this::enroll_factor_details);
    app.post("/challenge_factor", this::challenge_factor);
    app.post("/verify_factor", this::verify_factor);
  }


  public void home(Context ctx) {
    if (ctx.sessionAttribute("arrayFactorList") != null) {
      ArrayList<String> factorIdList = ctx.sessionAttribute("factorIdList");
      ArrayList<Factor> factorList = ctx.sessionAttribute("arrayFactorList");
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("factorList", factorList);
      System.out.println(jteParams);
      ctx.render("home.jte", jteParams);
    } else {
      ctx.render("home.jte");
    }
  }

  public String buildCode(Map<String, List<String>> map) {
    String code = "";
    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
      String thisValue = entry.getValue().get(0);
      code += thisValue;
    }
    return code;
  }

  public void verify_factor(Context ctx) {
    String code = buildCode(ctx.formParamMap());
    String challengeId = ctx.sessionAttribute("currentChallengeId");
    String factorType = ctx.sessionAttribute("currentFactorType");

    VerifyFactorOptions options = MfaApi.VerifyFactorOptions.builder()
      .authenticationChallengeId(challengeId)
      .code(code)
      .build();
    try {
      VerifyFactorResponse response = workos.mfa.verifyFactor(options);
      Boolean isValid = response.valid;
      String stringValid = isValid.toString();

      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("factorId", response.challenge.id);
      jteParams.put("createdAt", response.challenge.createdAt);
      jteParams.put("expiresAt", response.challenge.expiresAt);
      jteParams.put("valid", stringValid);
      jteParams.put("type", factorType);

      ctx.render("challenge_result.jte", jteParams);
    } catch (Exception e) {
      if (e.equals(null)) {
        ctx.render("error.jte");
      }
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("error", e.getMessage());
      ctx.render("error.jte", jteParams);
    }
  }

  public void challenge_factor(Context ctx) {
    String factorType = ctx.formParam("type");
    ctx.sessionAttribute("currentFactorType", factorType);
    String smsCode = ctx.formParam("sms_message");
    String currentFactorId = ctx.sessionAttribute("currentFactorId");

    if (factorType.equals("sms")) {
      ChallengeFactorOptions options = MfaApi.ChallengeFactorOptions.builder()
        .authenticationFactorId(currentFactorId)
        .smsTemplate(smsCode)
        .build();
      Challenge challenge = workos.mfa.challengeFactor(options);
      ctx.sessionAttribute("currentChallengeId", challenge.id);

      ctx.render("challenge_factor.jte");
    }

    if (factorType.equals("totp")) {
      ChallengeFactorOptions options = MfaApi.ChallengeFactorOptions.builder()
        .authenticationFactorId(currentFactorId)
        .build();
      try {
        Challenge challenge = workos.mfa.challengeFactor(options);
        ctx.sessionAttribute("currentChallengeId", challenge.id);
      } catch (Exception e) {
        if (e.equals(null)) {
          ctx.render("error.jte");
        }
        Map<String, Object> jteParams = new HashMap<>();
        jteParams.put("error", e.getMessage());
        ctx.render("error.jte", jteParams);
      }
      ctx.render("challenge_factor.jte");
    }
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

    switch (currentFactor.type) {
      case "sms":
        jteParams.put("phoneNumber", currentFactor.sms.phoneNumber);
        jteParams.put("factorId", currentFactor.id);
        jteParams.put("createdAt", currentFactor.createdAt);
        jteParams.put("type", currentFactor.type);
        break;
      case "totp":
        jteParams.put("factorId", currentFactor.id);
        jteParams.put("createdAt", currentFactor.createdAt);
        jteParams.put("type", currentFactor.type);
        jteParams.put("qrCode", currentFactor.totp.qrCode);
        break;
      default:
        String error = "Invalid type";
        String errorMessage = "Type must be either 'sms' or 'totp'";
        jteParams.put("error", error);
        jteParams.put("errorMessage", errorMessage);
        ctx.render("error.jte", jteParams);
    }

    ctx.render("factor_detail.jte", jteParams);
  }

  public void enroll_factor_details(Context ctx) {
    ctx.render("enroll_factor.jte");
  }

  public void enroll_totp_factor(Context ctx) {
    JsonNode jsonNode = ctx.bodyAsClass(JsonNode.class);
    String issuer = jsonNode.get("issuer").asText();
    String user = jsonNode.get("user").asText();
    EnrollFactorOptions options;

    options = EnrollFactorOptions.builder()
      .type("totp")
      .issuer(issuer)
      .user(user)
      .build();

    try {
      Factor factor = workos.mfa.enrollFactor(options);
      String factorId = factor.id;

      if (ctx.sessionAttribute("factorList") != null) {
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
      ctx.status(200).json(factor);
    } catch (Exception e) {
      if (e.equals(null)) {
        ctx.render("error.jte");
      }
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("error", e.getMessage());
      ctx.render("error.jte", jteParams);
    }
  }

  public void enroll_sms_factor(Context ctx) {
    String phoneNumber = ctx.formParam("phone_number");
    EnrollFactorOptions options;

    options = EnrollFactorOptions.builder()
      .type("sms")
      .phoneNumber(phoneNumber)
      .build();

    try {
      Factor factor = workos.mfa.enrollFactor(options);
      String factorId = factor.id;

      if (ctx.sessionAttribute("factorList") != null) {
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
    } catch (Exception e) {
      if (e.equals(null)) {
        ctx.render("error.jte");
      }
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("error", e.getMessage());
      ctx.render("error.jte", jteParams);
    }
  }

  public void clear_session(Context ctx) {
    ctx.sessionAttribute("factorList", null);
    ctx.sessionAttribute("arrayFactorList", null);
    ctx.sessionAttribute("factorIdList", null);
    ctx.sessionAttribute("currentFactorType", null);
    ctx.sessionAttribute("currentChallengeId", null);
    ctx.sessionAttribute("currentFactorId", null);
    ctx.sessionAttribute("currentFactor", null);
    ctx.redirect("/");
  }

  public static void main(String[] args) {
    new MfaApp();
  }
}

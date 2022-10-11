package com.workos.java.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.directorysync.DirectorySyncApi.ListDirectoriesOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoriesOptions.ListDirectoriesOptionsBuilder;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryGroupOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryUserOptions;
import com.workos.directorysync.models.DirectoryGroupList;
import com.workos.directorysync.models.DirectoryList;
import com.workos.directorysync.models.DirectoryUserList;
import com.workos.directorysync.models.Group;
import com.workos.directorysync.models.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;


public class AuditLogsApp {
  private final WorkOS workos;
  private final String clientId;

  public AuditLogsApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();

    Javalin app = Javalin.create(config -> {
      config.addStaticFiles("src/resources", Location.EXTERNAL);
    }).start(7001);

    workos = new WorkOS(env.get("WORKOS_API_KEY"));
    clientId = env.get("WORKOS_CLIENT_ID");

    app.get("/", this::isLoggedIn);
    app.post("/set_org", this::setOrg);
    app.get("/send_events", this::sendEvents);
    app.get("/export_events", this::exportEvents);
    app.get("logout", this::logout);
  }

  public void setOrg(Context ctx) {
    System.out.println("hit setOrg");
    String org = ctx.formParam("org");
    System.out.println(org);
    ctx.sessionAttribute("org", org);

    ctx.redirect("/");
  }

  public void isLoggedIn(Context ctx) {
    System.out.println("hit isLoggedIn");
    if (ctx.sessionAttribute("org") != null){
      ctx.render("send_events.jte", Collections.singletonMap("org", ctx.sessionAttribute("org")));
    } else {
      System.out.println("hit the else");
      ctx.render("home.jte");
    }
  }

  public void sendEvents(Context ctx) {

  }

  public void exportEvents(Context ctx) {

  }

  public void logout(Context ctx ) {
    ctx.sessionAttribute("org", null);
    ctx.redirect("/");
  }

  public static void main(String[] args) {
    new AuditLogsApp();
  }
}

package com.workos.java.examples;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.common.http.PaginationParams;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryGroupOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryUserOptions;
import com.workos.directorysync.models.DirectoryGroupList;
import com.workos.directorysync.models.DirectoryList;
import com.workos.directorysync.models.DirectoryUserList;
import com.workos.directorysync.models.Group;
import com.workos.directorysync.models.User;
import com.workos.webhooks.models.Webhook;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class DirectorySyncApp {
  private Javalin app;

  private WorkOS workos;

  private ObjectMapper mapper = new ObjectMapper();

  public DirectorySyncApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create().start(7003);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get("/directories", this::directories);
    app.get("/directories/delete/{directoryId}", this::deleteDirectory);
    app.get("/directories/{directoryId}/users", this::directoryUsers);
    app.get("/directories/{directoryId}/users/{userId}", this::directoryUser);
    app.get("/directories/{directoryId}/groups", this::directoryGroups);
    app.get("/directories/{directoryId}/groups/{groupId}", this::directoryGroup);
  }

  public Context directories(Context ctx) {
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");
    String deleteResult = ctx.queryParam("deleteResult");

    PaginationParams.Builder paginationParams = PaginationParams.builder();

    if (after != null) {
      paginationParams.after(after);
    }

    if (before != null) {
      paginationParams.before(before);
    }

    DirectoryList directoryList = workos.directorySync.listDirectories(paginationParams.build());

    HashMap jteParams = new HashMap();
    jteParams.put("directories", directoryList);
    jteParams.put("deleteResult", deleteResult);

    return ctx.render("directories.jte", jteParams);
  }

  public void deleteDirectory(Context ctx) {
    String directoryId = ctx.pathParam("directoryId");
    String deleteResult;

    try {
      workos.directorySync.deleteDirectory(directoryId);
      deleteResult = "succeeded";
    } catch (Error e) {
      deleteResult = "failed";
    }

    ctx.redirect("/directories?deleteResult=" +  deleteResult);
  }

  public Context directoryUsers(Context ctx) {
    String directoryId = ctx.pathParam("directoryId");
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");

    ListDirectoryUserOptions.Builder options = ListDirectoryUserOptions.builder()
      .directory(directoryId);

    if (after != null) {
      options.after(after);
    }

    if (before != null) {
      options.before(before);
    }

    DirectoryUserList directoryUserList = workos.directorySync.listDirectoryUsers(
      options.build());

    Map jteParams = new HashMap();
    jteParams.put("directoryUsers", directoryUserList);
    jteParams.put("directoryId", directoryId);

    return ctx.render("directoryUsers.jte", jteParams);
  }

  public Context directoryUser(Context ctx) {
    String userId = ctx.pathParam("userId");

    User directoryUser = workos.directorySync.getDirectoryUser(userId);

    String directoryUserJson;
    Boolean error = false;
    try {
      directoryUserJson = mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(directoryUser);
    } catch (JsonProcessingException e) {
      directoryUserJson = "{}";
      error = true;
    }

    Map jteParams = new HashMap();
    jteParams.put("directoryUser", directoryUserJson);
    jteParams.put("hasError", error);

    return ctx.render("directoryUser.jte", jteParams);
  }

  public Context directoryGroups(Context ctx) {
    String directoryId = ctx.pathParam("directoryId");
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");

    ListDirectoryGroupOptions.Builder options = ListDirectoryGroupOptions.builder()
      .directory(directoryId);

    if (after != null) {
      options.after(after);
    }

    if (before != null) {
      options.before(before);
    }

    DirectoryGroupList directoryGroupList = workos.directorySync.listDirectoryGroups(
      options.build());

    Map jteParams = new HashMap();
    jteParams.put("directoryGroups", directoryGroupList);
    jteParams.put("directoryId", directoryId);

    return ctx.render("directoryGroups.jte", jteParams);
  }

  public Context directoryGroup(Context ctx) {
    String groupId = ctx.pathParam("groupId");

    Group directoryGroup = workos.directorySync.getDirectoryGroup(groupId);

    String directoryGroupJson;
    Boolean error = false;
    try {
      directoryGroupJson = mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(directoryGroup);
    } catch (JsonProcessingException e) {
      directoryGroupJson = "{}";
      error = true;
    }

    Map jteParams = new HashMap();
    jteParams.put("directoryGroup", directoryGroupJson);
    jteParams.put("hasError", error);

    return ctx.render("directoryGroup.jte", jteParams);
  }

  public static void main(String[] args) {
    new DirectorySyncApp();
  }
}

package com.workos.java.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.common.http.PaginationParams;
import com.workos.common.http.PaginationParams.PaginationParamsBuilder;
import com.workos.common.models.ListMetadata;
import com.workos.directorysync.DirectorySyncApi.ListDirectoriesOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryGroupOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryUserOptions;
import com.workos.organizations.OrganizationsApi.ListOrganizationsOptions;

import com.workos.sso.SsoApi;
import com.workos.sso.SsoApi.ListConnectionsOptions.ListConnectionsOptionsPaginationParamsBuilder;
import com.workos.sso.models.Connection;
import com.workos.sso.models.ConnectionList;
import com.workos.sso.SsoApi.ListConnectionsOptions;


import com.workos.organizations.models.OrganizationList;
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
import java.util.List;


public class DirectorySyncApp {
  private final WorkOS workos;

  private final ObjectMapper mapper = new ObjectMapper();
  private ListConnectionsOptions listConnectionsOptions;

  public DirectorySyncApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();

    Javalin app = Javalin.create(config -> {
      config.addStaticFiles("src/resources", Location.EXTERNAL);
    }).start(7001);


    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    app.get("/", this::listOrganizations);
    app.get("/directories", this::directories);
    app.get("/directories/delete/{directoryId}", this::deleteDirectory);
    app.get("/directories/{directoryId}/users", this::directoryUsers);
    app.get("/directories/{directoryId}/users/{userId}", this::directoryUser);
    app.get("/directories/{directoryId}/groups", this::directoryGroups);
    app.get("/directories/{directoryId}/groups/{groupId}", this::directoryGroup);
  }

  public void directories(Context ctx) {
    String organization = ctx.queryParam("id");
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");
    String deleteResult = ctx.queryParam("deleteResult");
    Integer limit = 5;

    ListDirectoriesOptions.ListDirectoriesOptionsBuilder options =
      ListDirectoriesOptions.builder().organization(organization);

    if (after != null) {
      options.after(after);
    }

    if (before != null) {
      options.before(before);
    }

    if (limit != null) {
      options.limit(limit);
    }

    DirectoryList directoryList = workos.directorySync.listDirectories(options.build());

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("directories", directoryList);
    jteParams.put("deleteResult", deleteResult);

    ctx.render("directories.jte", jteParams);
  }

  public void listOrganizations(Context ctx) {
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");

    List<String> domains = List.of("foo-corp.com");

    ListOrganizationsOptions options =
      ListOrganizationsOptions.builder().limit(5).build();

    if (after != null) {
      options.put("after", after);
    }

    if (before != null) {
      options.put("before", before);
    }

    OrganizationList organizationList = workos.organizations.listOrganizations(options);
    System.out.println(organizationList);

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("organizations", organizationList);

    ctx.render("home.jte", jteParams);
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

    ctx.redirect("/directories?deleteResult=" + deleteResult);
  }

  public void directoryUsers(Context ctx) {
    String directoryId = ctx.pathParam("directoryId");
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");

    ListDirectoryUserOptions.ListDirectoryUserOptionsBuilder options =
        ListDirectoryUserOptions.builder().directory(directoryId);

    if (after != null) {
      options.after(after);
    }

    if (before != null) {
      options.before(before);
    }

    DirectoryUserList directoryUserList = workos.directorySync.listDirectoryUsers(options.build());

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("directoryUsers", directoryUserList);
    jteParams.put("directoryId", directoryId);

    ctx.render("directoryUsers.jte", jteParams);
  }

  public void directoryUser(Context ctx) {
    String userId = ctx.pathParam("userId");

    User directoryUser = workos.directorySync.getDirectoryUser(userId);

    String directoryUserJson;
    boolean error = false;
    try {
      directoryUserJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(directoryUser);
    } catch (JsonProcessingException e) {
      directoryUserJson = "{}";
      error = true;
    }

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("directoryUser", directoryUserJson);
    jteParams.put("directoryId", ctx.pathParam("directoryId"));
    jteParams.put("hasError", error);

    ctx.render("directoryUser.jte", jteParams);
  }

  public void directoryGroups(Context ctx) {
    String directoryId = ctx.pathParam("directoryId");
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");

    ListDirectoryGroupOptions.ListDirectoryGroupOptionsBuilder options =
        ListDirectoryGroupOptions.builder().directory(directoryId);

    if (after != null) {
      options.after(after);
    }

    if (before != null) {
      options.before(before);
    }

    DirectoryGroupList directoryGroupList =
        workos.directorySync.listDirectoryGroups(options.build());

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("directoryGroups", directoryGroupList);
    jteParams.put("directoryId", directoryId);

    ctx.render("directoryGroups.jte", jteParams);
  }

  public void directoryGroup(Context ctx) {
    String groupId = ctx.pathParam("groupId");

    Group directoryGroup = workos.directorySync.getDirectoryGroup(groupId);

    String directoryGroupJson;
    boolean error = false;
    try {
      directoryGroupJson =
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(directoryGroup);
    } catch (JsonProcessingException e) {
      directoryGroupJson = "{}";
      error = true;
    }

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("directoryGroup", directoryGroupJson);
    jteParams.put("directoryId", ctx.pathParam("directoryId"));
    jteParams.put("hasError", error);

    ctx.render("directoryGroup.jte", jteParams);
  }

  public static void main(String[] args) {
    new DirectorySyncApp();
  }
}



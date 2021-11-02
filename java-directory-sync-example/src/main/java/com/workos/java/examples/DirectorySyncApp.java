package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.common.http.PaginationParams;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryGroupOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryUserOptions;
import com.workos.directorysync.models.Directory;
import com.workos.directorysync.models.DirectoryGroupList;
import com.workos.directorysync.models.DirectoryList;
import com.workos.directorysync.models.DirectoryUserList;
import com.workos.directorysync.models.Group;
import com.workos.directorysync.models.User;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectorySyncApp {
  private Javalin app;

  private WorkOS workos;

  public DirectorySyncApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create().start(7003);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));
//
//    DirectoryGroupList directoryGroupList = workos.directorySync.listDirectoryGroups(
//      ListDirectoryGroupOptions
//        .builder()
//        .directory("directory_01FHR8XRSWGW4W0NP9DPHAWQXM")
//        .build()
//    );
//
//    List<Group> directoryGroups = directoryGroupList.getData();
//
//    System.out.println(directoryGroups);
//
//    DirectoryUserList directoryUserList = workos.directorySync.listDirectoryUsers(
//      ListDirectoryUserOptions
//        .builder()
//        .directory("directory_01FHR8XRSWGW4W0NP9DPHAWQXM")
//        .build()
//    );
//
//    List<User> directoryUsers = directoryUserList.getData();
//
//    System.out.println(directoryUsers);

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get("/directories", this::directories);
    app.get("/directories/delete/{directoryId}", this::deleteDirectory);
    app.get("/directories/{directoryId}/users", this::directoryUsers);
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

    System.out.println(jteParams);

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

    ListDirectoryUserOptions.Builder listDirectoryOptions = ListDirectoryUserOptions.builder()
      .directory(directoryId);

    if (after != null) {
      listDirectoryOptions.after(after);
    }

    if (before != null) {
      listDirectoryOptions.before(before);
    }

    DirectoryUserList directoryUserList = workos.directorySync.listDirectoryUsers(
      listDirectoryOptions.build());

    Map jteParams = new HashMap();
    jteParams.put("directoryUsers", directoryUserList);
    jteParams.put("directoryId", directoryId);

    return ctx.render("directoryUsers.jte", jteParams);
  }

  public static void main(String[] args) {
    new DirectorySyncApp();
  }
}

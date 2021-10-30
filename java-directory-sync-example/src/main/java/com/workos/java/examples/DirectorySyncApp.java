package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryGroupOptions;
import com.workos.directorysync.DirectorySyncApi.ListDirectoryUserOptions;
import com.workos.directorysync.models.Directory;
import com.workos.directorysync.models.DirectoryGroupList;
import com.workos.directorysync.models.DirectoryList;
import com.workos.directorysync.models.DirectoryUserList;
import com.workos.directorysync.models.Group;
import com.workos.directorysync.models.User;
import io.javalin.Javalin;
import java.util.List;
import java.util.Map;

public class DirectorySyncApp {
  private Javalin app;

  private WorkOS workos;

  public DirectorySyncApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create().start(7001);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    // TODO: figure out how to default to no pagination params
    DirectoryList directoryList = workos.directorySync.listDirectories();

    List<Directory> directories = directoryList.getData();

    DirectoryGroupList directoryGroupList = workos.directorySync.listDirectoryGroups(
      ListDirectoryGroupOptions
        .builder()
        .directory(directories.get(0).getId())
        .build()
    );

    List<Group> directoryGroups = directoryGroupList.getData();

    DirectoryUserList directoryUserList = workos.directorySync.listDirectoryUsers(
      ListDirectoryUserOptions
        .builder()
        .group(directoryGroups.get(0).getId())
        .build()
    );

    List<User> directoryUsers = directoryUserList.getData();

    app.get("/", ctx -> ctx.render("home.jte"));
  }

  public static void main(String[] args) {
    new DirectorySyncApp();
  }
}

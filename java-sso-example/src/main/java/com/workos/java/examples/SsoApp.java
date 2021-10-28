package com.workos.java.examples;

import io.javalin.Javalin;

public class SsoApp {
  private Javalin app;

  public SsoApp() {
    app = Javalin.create().start(7000);

    System.out.println("Java SSO Example Application running: http://localhost:7000");

    app.get("/", ctx -> ctx.result("Hello World"));
  }

  public static void main(String[] args) {
    new SsoApp();
  }
}

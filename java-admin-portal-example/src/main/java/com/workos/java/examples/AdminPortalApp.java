package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.portal.PortalApi.GeneratePortalLinkOptions;
import com.workos.portal.models.Intent;
import com.workos.portal.models.Link;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AdminPortalApp {
  private Javalin app;

  private WorkOS workos;

  private String organizationId = "org_01FHB9XB2XJDBJ6CN1AR404D4X";

  public AdminPortalApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();

    app = Javalin.create().start(7001);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get("/admin_portal/sso", this::ssoPortal);
    app.get("/admin_portal/dsync", this::dsyncPortal);
  }

  public void ssoPortal(Context ctx) {
    Link url = workos.portal.generateLink(
      GeneratePortalLinkOptions
        .builder()
        .organization(organizationId)
        .intent(Intent.Sso)
        .build()
    );

    ctx.redirect(url.link);
  }

  public void dsyncPortal(Context ctx) {
    Link url = workos.portal.generateLink(
      GeneratePortalLinkOptions
        .builder()
        .organization(organizationId)
        .intent(Intent.DirectorySync)
        .build()
    );

    ctx.redirect(url.link);
  }

  public static void main(String[] args) {
    new AdminPortalApp();
  }
}

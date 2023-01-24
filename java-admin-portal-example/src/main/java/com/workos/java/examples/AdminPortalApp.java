package com.workos.java.examples;

import com.workos.WorkOS;
import com.workos.organizations.OrganizationsApi.CreateOrganizationOptions;
import com.workos.organizations.OrganizationsApi.ListOrganizationsOptions;
import com.workos.organizations.models.Organization;
import com.workos.organizations.models.OrganizationList;
import com.workos.portal.PortalApi.GeneratePortalLinkOptions;
import com.workos.portal.models.Intent;
import com.workos.portal.models.Link;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.List;

public class AdminPortalApp {
  private WorkOS workos;

  public AdminPortalApp() {
    Dotenv env = Dotenv.configure().directory("../.env").load();

    Javalin app =
        Javalin.create(
                config -> {
                  config.addStaticFiles("src/resources", Location.EXTERNAL);
                })
            .start(7001);

    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get("/admin-portal/generateLink", this::generateLink);
    app.post("/provision-enterprise", this::provisionEnterprise);
  }

  public void generateLink(Context ctx) {
    String organizationId = ctx.cookie("organizationId");
    Intent intent = null;
    switch(ctx.queryParam("intent")) {
      case "sso":
        intent = Intent.Sso;
        break;
      case "audit_logs":
        intent = Intent.AuditLogs;
        break;
      case "dsync":
        intent = Intent.DirectorySync;
      case "log_streams":
        intent = Intent.LogStreams;
    }

    Link url =
        workos.portal.generateLink(
            GeneratePortalLinkOptions.builder()
                .organization(organizationId)
                .intent(intent)
                .build());

    ctx.redirect(url.link);
  }

  public void provisionEnterprise(Context ctx) {
    String organizationName = ctx.formParam("org");
    String domain = ctx.formParam("domain");

    List<String> domains = List.of(domain.split(" "));

    OrganizationList list =
        workos.organizations.listOrganizations(
            ListOrganizationsOptions.builder().domains(domains).build());

    String organizationId;

    if (list.data.size() == 0) {
      Organization organization =
          workos.organizations.createOrganization(
              CreateOrganizationOptions.builder().name(organizationName).domains(domains).build());

      organizationId = organization.id;
    } else {
      organizationId = list.data.get(0).id;
    }

    ctx.cookie("organizationId", organizationId);

    ctx.render("provision.jte");
  }

  public static void main(String[] args) {
    new AdminPortalApp();
  }
}

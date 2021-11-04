package com.workos.java.examples;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.organizations.OrganizationsApi.CreateOrganizationOptions;
import com.workos.organizations.OrganizationsApi.ListOrganizationsOptions;
import com.workos.organizations.models.Organization;
import com.workos.organizations.models.OrganizationList;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrganizationsApp {
  private Javalin app;

  private WorkOS workos;

  private ObjectMapper mapper = new ObjectMapper();

  public OrganizationsApp() {
    Map<String, String> env = System.getenv();

    app = Javalin.create().start(7006);
    workos = new WorkOS(env.get("WORKOS_API_KEY"));

    app.get("/", ctx -> ctx.render("home.jte"));
    app.get("/organizations", this::organizations);
    app.get("/organizations/{organizationId}", this::organization);
    app.get("/organizations/delete/{organizationId}", this::deleteOrganization);
    app.post("/organizations/create", this::createOrganization);
  }

  public Context organizations(Context ctx) {
    String after = ctx.queryParam("after");
    String before = ctx.queryParam("before");
    String deleteResult = ctx.queryParam("deleteResult");
    String createSucceeded = ctx.queryParam("createSucceeded");
    String createFailed = ctx.queryParam("createFailed");

    ListOrganizationsOptions.Builder options = ListOrganizationsOptions.builder();

    if (after != null) {
      options.after(after);
    }

    if (before != null) {
      options.before(before);
    }

    OrganizationList organizationList = workos.organizations.listOrganizations(options.build());

    HashMap jteParams = new HashMap();
    jteParams.put("organizations", organizationList);
    jteParams.put("deleteResult", deleteResult);
    jteParams.put("createFailed", createFailed);
    jteParams.put("createSucceeded", createSucceeded);

    return ctx.render("organizations.jte", jteParams);
  }

  public Context organization(Context ctx) {
    String organizationId = ctx.pathParam("organizationId");

    Organization organization = workos.organizations.getOrganization(organizationId);

    String organizationJson;
    Boolean error = false;
    try {
      organizationJson = mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(organization);
    } catch (JsonProcessingException e) {
      organizationJson = "{}";
      error = true;
    }

    Map jteParams = new HashMap();
    jteParams.put("organization", organizationJson);
    jteParams.put("hasError", error);

    return ctx.render("organization.jte", jteParams);
  }

  public void createOrganization(Context ctx) {
    try {
      String name = ctx.formParam("name");
      String allowProfilesOutsideOrg = ctx.formParam("allowProfilesOutsideOrg");
      String domains = ctx.formParam("domains");

      CreateOrganizationOptions options = CreateOrganizationOptions.builder()
        .allowProfilesOutsideOrganization(Boolean.parseBoolean(allowProfilesOutsideOrg))
        .domains(!domains.isEmpty()
          ? Arrays.asList(domains.split(","))
          : Collections.EMPTY_LIST
        )
        .name(name)
        .build();

      Organization organization = workos.organizations.createOrganization(options);
      System.out.println("organization created: " + organization);
      ctx.redirect("/organizations?createSucceeded=" + organization.name);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error creating organization: " + e.getMessage());
      ctx.redirect("/organizations?createFailed=" + e.getMessage());
    }
  }

  public void deleteOrganization(Context ctx) {
    String organizationId = ctx.pathParam("organizationId");
    String deleteResult;

    try {
      workos.organizations.deleteOrganization(organizationId);
      deleteResult = "succeeded";
    } catch (Error e) {
      deleteResult = "failed";
    }

    ctx.redirect("/organizations?deleteResult=" +  deleteResult);
  }

  public static void main(String[] args) {
    new OrganizationsApp();
  }
}

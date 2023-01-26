package com.workos.java.examples;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workos.WorkOS;
import com.workos.auditlogs.AuditLogsApi.CreateAuditLogEventOptions;
import com.workos.auditlogs.AuditLogsApi.CreateAuditLogEventRequestOptions;
import com.workos.auditlogs.AuditLogsApi.CreateAuditLogExportOptions;
import com.workos.auditlogs.models.AuditLogExport;
import com.workos.organizations.OrganizationsApi.ListOrganizationsOptions;
import com.workos.organizations.models.Organization;
import com.workos.organizations.models.OrganizationList;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.util.UUID;
import java.time.*;


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

    app.get("/", this::home);
    app.get("/set_org", this::setOrg);
    app.get("/send_event", this::sendEvent);
    app.get("/export_events", this::exportEvents);
    app.post("/get_events", this::getEvents);
    app.get("logout", this::logout);
  }

  public void home(Context ctx) {
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

  public void setOrg(Context ctx) {
    String orgId = ctx.queryParam("id");

    try {
      Organization org = workos.organizations.getOrganization(orgId);

      String orgName = org.name;
      ctx.sessionAttribute("org_id", orgId);
      ctx.sessionAttribute("org_name", orgName);

      LocalDateTime now =  LocalDateTime.now();
      LocalDateTime sameDayLastMonth = now.minusMonths(1);
      Date dateNow = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
      Date dateSameDayLastMonth = Date.from(sameDayLastMonth.atZone(ZoneId.systemDefault()).toInstant());

      CreateAuditLogEventOptions options =
        CreateAuditLogEventOptions.builder()
          .action("user.organization_set")
          .occurredAt(new Date())
          .version(1)
          .actor("user_id", "user", "Jon Smith", Map.of("role", "admin"))
          .target("team_id", "team", null, Map.of("extra", "data"))
          .context("1.1.1.1", "Chrome/104.0.0.0")
          .metadata(Map.of("extra", "data"))
          .build();
      String uniqueID = UUID.randomUUID().toString();
      CreateAuditLogEventRequestOptions requestOptions =
        CreateAuditLogEventRequestOptions.builder()
          .idempotencyKey(uniqueID)
          .build();

      workos.auditLogs.createEvent(ctx.sessionAttribute("org_id"), options, requestOptions);

      ctx.redirect("/send_event");
    }
    catch (Exception e) {
      System.out.println(e);
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("error", e.toString());

      ctx.render("error.jte", jteParams);
    }
  }


  public void sendEvent(Context ctx) {
    String eventType = "0";
    ArrayList<String> events = new ArrayList<String>();
    events.add("user.signed_in");
    events.add("user.logged_out");
    events.add("user.organization_deleted");
    events.add("user.connection_deleted");
    String event = events.get(Integer.parseInt(eventType));
    CreateAuditLogEventOptions options =
      CreateAuditLogEventOptions.builder()
        .action(event)
        .occurredAt(new Date())
        .version(1)
        .actor("user_id", "user", "Jon Smith", Map.of("role", "admin"))
        .target("team_id", "team", null, Map.of("extra", "data"))
        .context("1.1.1.1", "Chrome/104.0.0.0")
        .metadata(Map.of("extra", "data"))
        .build();
    String uniqueID = UUID.randomUUID().toString();
    CreateAuditLogEventRequestOptions requestOptions =
      CreateAuditLogEventRequestOptions.builder()
        .idempotencyKey(uniqueID)
        .build();
    workos.auditLogs.createEvent(ctx.sessionAttribute("org_id"), options, requestOptions);

    ctx.render("send_events.jte");
  }

  public void exportEvents(Context ctx) {
    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("org_name", ctx.sessionAttribute("org_name"));
    jteParams.put("org_id", ctx.sessionAttribute("org_id"));

    ctx.render("export_events.jte", jteParams);
  }

  public void getEvents(Context ctx) {
    String eventType = ctx.formParam("event");
    LocalDateTime now =  LocalDateTime.now();
    LocalDateTime sameDayLastMonth = now.minusMonths(1);
    Date dateNow = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    Date dateSameDayLastMonth = Date.from(sameDayLastMonth.atZone(ZoneId.systemDefault()).toInstant());

    if(Integer.parseInt(eventType) == 0) {
      CreateAuditLogExportOptions options = CreateAuditLogExportOptions.builder()
        .organizationId(ctx.sessionAttribute("org_id"))
        .rangeStart(dateSameDayLastMonth)
        .rangeEnd(dateNow)
        .build();

      AuditLogExport auditLogExport = workos.auditLogs.createExport(options);
      String csvId = auditLogExport.id;
      ctx.sessionAttribute("csv_id", csvId);
    }
    if(Integer.parseInt(eventType) == 1) {
      AuditLogExport auditLogExport =
        workos.auditLogs.getExport(ctx.sessionAttribute("csv_id"));
      ctx.redirect(auditLogExport.url);
    }

    Map<String, Object> jteParams = new HashMap<>();
    jteParams.put("org_name", ctx.sessionAttribute("org_name"));
    jteParams.put("org_id", ctx.sessionAttribute("org_id"));

    ctx.render("export_events.jte", jteParams);
  }

  public void logout(Context ctx ) {
    ctx.sessionAttribute("org_id", null);
    ctx.sessionAttribute("org_name", null);
    ctx.sessionAttribute("csv_id", null);
    ctx.redirect("/");
  }

  public static void main(String[] args) {
    new AuditLogsApp();
  }
}

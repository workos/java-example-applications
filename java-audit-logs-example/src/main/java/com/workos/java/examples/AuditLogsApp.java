package com.workos.java.examples;
import com.workos.WorkOS;
import com.workos.auditlogs.AuditLogsApi.CreateAuditLogEventOptions;
import com.workos.auditlogs.AuditLogsApi.CreateAuditLogEventRequestOptions;
import com.workos.auditlogs.AuditLogsApi.CreateAuditLogExportOptions;
import com.workos.auditlogs.models.AuditLogExport;
import com.workos.common.models.Order;
import com.workos.organizations.OrganizationsApi.ListOrganizationsOptions;
import com.workos.organizations.models.Organization;
import com.workos.organizations.models.OrganizationList;
import com.workos.portal.PortalApi.GeneratePortalLinkOptions;
import com.workos.portal.models.Link;
import com.workos.portal.models.Intent;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    app.post("/send_event", this::sendEvent);
    app.post("/get_events", this::getEvents);
    app.get("logout", this::logout);
    app.get("/admin_portal", this::adminPortal);
  }

  public void home(Context ctx) {
    String value = ctx.sessionAttribute("org_id");

    if (value == null) {
      String after = ctx.queryParam("after");
      String before = ctx.queryParam("before");
      List<String> domains = List.of("foo-corp.com");
      ListOrganizationsOptions options =
        ListOrganizationsOptions.builder().limit(5).order(Order.Desc).build();

      if (after != null) {
        options.put("after", after);
      }

      if (before != null) {
        options.put("before", before);
      }

      OrganizationList organizationList = workos.organizations.listOrganizations(options);
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("organizations", organizationList);

      ctx.render("home.jte", jteParams);
    } else {
      LocalDateTime now =  LocalDateTime.now();
      LocalDateTime sameDayLastMonth = now.minusMonths(1);
      String dateNow = Date.from(now.atZone(ZoneId.systemDefault()).toInstant()).toString();
      String dateSameDayLastMonth = Date.from(sameDayLastMonth.atZone(ZoneId.systemDefault()).toInstant()).toString();

      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("org_name", ctx.sessionAttribute("org_name"));
      jteParams.put("org_id", ctx.sessionAttribute("org_id"));
      jteParams.put("last_month_iso", dateSameDayLastMonth);
      jteParams.put("today_iso", dateNow);

      ctx.render("send_events.jte", jteParams);
    }
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

      ctx.redirect("/");
    }
    catch (Exception e) {
      System.out.println(e);
      Map<String, Object> jteParams = new HashMap<>();
      jteParams.put("error", e.toString());

      ctx.render("error.jte", jteParams);
    }
  }


  public void sendEvent(Context ctx) {
    Integer eventVersion = Integer.parseInt(ctx.formParam("event-version"));
    String actorName = ctx.formParam("actor-name");
    String actorType = ctx.formParam("actor-type");
    String targetName = ctx.formParam("target-name");
    String targetType = ctx.formParam("target-type");

    try{
      CreateAuditLogEventOptions options =
        CreateAuditLogEventOptions.builder()
          .action("user.organization_deleted")
          .occurredAt(new Date())
          .version(eventVersion)
          .actor("user_id", actorType, actorName, Map.of("role", "admin"))
          .target("team_id", targetType, targetName, Map.of("extra", "data"))
          .context("1.1.1.1", "Chrome/104.0.0.0")
          .metadata(Map.of("extra", "data"))
          .build();

      String uniqueID = UUID.randomUUID().toString();
      CreateAuditLogEventRequestOptions requestOptions =
        CreateAuditLogEventRequestOptions.builder()
          .idempotencyKey(uniqueID)
          .build();
      workos.auditLogs.createEvent(ctx.sessionAttribute("org_id"), options, requestOptions);

      ctx.redirect("/");
    } catch(Exception e){
      System.out.println(e);
      ctx.redirect("/");
    }
  }


  public void getEvents(Context ctx) {
    String eventType = ctx.formParam("event");
    LocalDateTime now =  LocalDateTime.now();
    LocalDateTime sameDayLastMonth = now.minusMonths(1);
    Date dateNow = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    Date dateSameDayLastMonth = Date.from(sameDayLastMonth.atZone(ZoneId.systemDefault()).toInstant());

    try {
      if(Integer.parseInt(ctx.formParam("event")) == 0) {
        List filterActions = new ArrayList();
        List filterActors = new ArrayList();
        List filterTargets = new ArrayList();


        if( ctx.formParam("filter-actions") != ""){
          filterActions.add(ctx.formParam("filter-actions"));

          CreateAuditLogExportOptions options = CreateAuditLogExportOptions.builder()
            .organizationId(ctx.sessionAttribute("org_id"))
            .rangeStart(dateSameDayLastMonth)
            .rangeEnd(dateNow)
            .actions(filterActions)
            .build();

          AuditLogExport auditLogExport = workos.auditLogs.createExport(options);
          String csvId = auditLogExport.id;
          ctx.sessionAttribute("csv_id", csvId);

          ctx.redirect("/");
        }
        if( ctx.formParam("filter-actors") != ""){
          filterActors.add(ctx.formParam("filter-actors"));

          CreateAuditLogExportOptions options = CreateAuditLogExportOptions.builder()
            .organizationId(ctx.sessionAttribute("org_id"))
            .rangeStart(dateSameDayLastMonth)
            .rangeEnd(dateNow)
            .actors(filterActors)
            .build();

          AuditLogExport auditLogExport = workos.auditLogs.createExport(options);
          String csvId = auditLogExport.id;
          ctx.sessionAttribute("csv_id", csvId);

          ctx.redirect("/");
        }
        if( ctx.formParam("filter-targets") != ""){
          filterTargets.add(ctx.formParam("filter-targets"));

          CreateAuditLogExportOptions options = CreateAuditLogExportOptions.builder()
            .organizationId(ctx.sessionAttribute("org_id"))
            .rangeStart(dateSameDayLastMonth)
            .rangeEnd(dateNow)
            .targets(filterTargets)
            .build();

          AuditLogExport auditLogExport = workos.auditLogs.createExport(options);

          String csvId = auditLogExport.id;
          ctx.sessionAttribute("csv_id", csvId);

          ctx.redirect("/");
        }

      } else if (Integer.parseInt(ctx.formParam("event")) == 1) {
        AuditLogExport auditLogExport =
          workos.auditLogs.getExport(ctx.sessionAttribute("csv_id"));
        ctx.redirect(auditLogExport.url);
      }
    } catch (Exception e) {
      System.out.println(e);
      ctx.redirect("/");
    }
  }

  public void adminPortal(Context ctx) {
    String intent = ctx.queryParam("intent");
    if (intent.equals("audit_logs")){
      GeneratePortalLinkOptions options = GeneratePortalLinkOptions.builder()
        .organization(ctx.sessionAttribute("org_id"))
        .intent(Intent.AuditLogs)
        .build();

      Link response = workos.portal.generateLink(options);
      String link = response.link;
      ctx.redirect(link);
    } else if (intent.equals("log_streams")){
      GeneratePortalLinkOptions options = GeneratePortalLinkOptions.builder()
        .organization(ctx.sessionAttribute("org_id"))
        .intent(Intent.LogStreams)
        .build();

      Link response = workos.portal.generateLink(options);
      String link = response.link;
      ctx.redirect(link);
    }
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

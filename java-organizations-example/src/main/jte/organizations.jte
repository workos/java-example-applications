@param com.workos.organizations.models.OrganizationList organizations
@param String deleteResult

!{var before = organizations.listMetadata.before;}
!{var after = organizations.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<style>
  tr {
    line-height: 40px
  }
</style>

<script>
  function deleteOrganization(organizationId, name) {
    if(confirm("Are you sure you want to delete organization '" + name + "'?")) {
      window.location.href="/organizations/delete/" + organizationId;
    }
  }
</script>

<div class="container" style="padding: 40px;">
  <h1>Organizations</h1>

  @if(deleteResult != null && deleteResult.equals("succeeded"))
    <div class="alert alert-success" role="alert">
      Delete organization success.
    </div>
  @endif

  @if(deleteResult != null && deleteResult.equals("failed"))
    <div class="alert alert-danger" role="alert">
      Delete organization failed.
    </div>
  @endif

  <h2>Create Organization</h2>
  <form action="/organizations/create" method="post">
    <div class="mb-3">
      <label class="form-label" for="name">Name</label>
      <input class="form-control" id="name" type="name" name="name" />
      <label class="form-label" for="domains">Domains</label>
      <input class="form-control" id="domains" type="textarea" name="domains" aria-describedby="domainsHelp" />
      <div id="domainsHelp" class="form-text">Separate domains by a comma.</div>
      <label class="form-label" for="allowProfilesOutsideOrg">Allow profiles outside organization</label>
      <input id="allowProfilesOutsideOrg" type="checkbox" name="allowProfilesOutsideOrg" />
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>

  <h2>Organization List</h2>
  <table class="table">
    <tr>
      <th>Organization ID</th>
      <th>Name</th>
      <th>Domains</th>
      <th>Delete</th>
    </tr>
    @for(var organization : organizations.data)
    <tr>
      <td><a href="/organizations/${organization.id}">${organization.id}</a></td>
      <td>${organization.name}</td>
      <td>
        @for(var domain : organization.domains)
          <div>${domain.domain}</div>
        @endfor
      </td>
      <td>
        <button class="btn btn-danger" onclick="deleteOrganization('${organization.id}', '${organization.name}')">Delete</button>
      </td>
    </tr>
    @endfor
  </table>
  @if(after != null)
    <a class="btn btn-primary" href="/organizations?after=${after}">Before</a>
  @endif
  @if(before != null)
  <a class="btn btn-primary" href="/organizations?before=${before}">Next</a>
  @endif
</div>

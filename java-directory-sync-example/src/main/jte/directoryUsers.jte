@param com.workos.directorysync.models.DirectoryUserList directoryUsers
@param String directoryId

!{var before = directoryUsers.listMetadata.before;}
!{var after = directoryUsers.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<style>
  tr {
    line-height: 40px
  }
</style>

<div class="container" style="padding: 40px;">
  <h1>Directory Users</h1>

  <a class="btn btn-link" href="/directories">Back to Directories</a>

  <table class="table">
    <tr>
      <th>Directory User ID</th>
      <th>Directory Id</th>
      <th>First Name</th>
      <th>Last Name</th>
      <th>User State</th>
      <th>Groups</th>
    </tr>
    @for(var directoryUser : directoryUsers.data)
      <tr>
        <td><a href="/directories/${directoryId}/users/${directoryUser.id}">${directoryUser.id}</a></td>
        <td>${directoryUser.directoryId}</td>
        <td>${directoryUser.firstName}</td>
        <td>${directoryUser.lastName}</td>
        <td>${directoryUser.state}</td>
        <td>
          @for(var group: directoryUser.groups)
          <div>${group.name}</div>
          @endfor
        </td>
      </tr>
    @endfor
  </table>
  @if(after != null)
    <a class="btn btn-primary" href="/directories/${directoryId}/users?after=${after}">Before</a>
  @endif
  @if(before != null)
    <a class="btn btn-primary" href="/directories/${directoryId}/users?before=${before}">Next</a>
  @endif
</div>

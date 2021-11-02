@param com.workos.directorysync.models.DirectoryList directories
@param String deleteResult

!{var before = directories.listMetadata.before;}
!{var after = directories.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<style>
  tr {
    line-height: 40px
  }
</style>

<script>
  function deleteDirectory(directoryId, name) {
    if(confirm("Are you sure you want to delete directory '" + name + "'?")) {
      window.location.href="/directories/delete/" + directoryId;
    }
  }
</script>

<div class="container" style="padding: 40px;">
  <h1>Directories</h1>

  @if(deleteResult != null && deleteResult.equals("succeeded"))
    <div class="alert alert-success" role="alert">
      Delete directory success.
    </div>
  @endif

  @if(deleteResult != null && deleteResult.equals("failed"))
    <div class="alert alert-danger" role="alert">
      Delete directory failed.
    </div>
  @endif

  <table class="table">
    <tr>
      <th>Directory ID</th>
      <th>Type</th>
      <th>Name</th>
      <th>Domain</th>
      <th>State</th>
      <th>Users</th>
      <th>Groups</th>
      <th>Delete</th>
    </tr>
    @for(var directory : directories.data)
    <tr>
      <td>${directory.id}</td>
      <td>${directory.name}</td>
      <td>${directory.type}</td>
      <td>${directory.domain}</td>
      <td>${directory.state}</td>
      <td>
        <a href="/directories/${directory.id}/users">Users</a>
      </td>
      <td>
        <a href="/directories/${directory.id}/groups">Groups</a>
      </td>
      <td>
        <button class="btn btn-danger" onclick="deleteDirectory('${directory.id}', '${directory.name}')">Delete</button>
      </td>
    </tr>
    @endfor
  </table>
  @if(after != null)
    <a class="btn btn-primary" href="/directories?after=${after}">Before</a>
  @endif
  @if(before != null)
  <a class="btn btn-primary" href="/directories?before=${before}">Next</a>
  @endif
</div>

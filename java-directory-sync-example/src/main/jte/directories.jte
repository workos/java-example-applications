@param com.workos.directorysync.models.DirectoryList directories
@param String deleteResult

!{var before = directories.listMetadata.before;}
!{var after = directories.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">

<script>
  function deleteDirectory(directoryId, name) {
    if(confirm("Are you sure you want to delete directory '" + name + "'?")) {
      window.location.href="/directories/delete/" + directoryId;
    }
  }
</script>

<div class="logged_in_nav">
  <div class="flex_column">
    <p>Directories List</p>
  </div>
  <div>
    <img src="/images/workos_logo.png" alt="workos logo">
  </div>
</div>

<div class='flex'>
  <div class="logged_in_div_left">
    <div>
      <h1>Your app,</h1>
      <h2>Enterprise Ready</h2>
    </div>
    <div>
      <a href="https://workos.com/" target="_blank"><button class='button'>WorkOS</button></a>
      <a href="https://workos.com/docs" target="_blank"><button class='button'>Documentation</button></a>
      <a href="https://workos.com/docs/reference" target="_blank"><button class='button'>API Reference</button></a>
      <a href="https://workos.com/blog" target="_blank"><button class='button'>Blog</button></a>
      <a href="/"><button class='button'>Home</button></a>
    </div>
  </div>
  <div class="logged_in_div_right">
    <div class="flex_column directory_container">
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
      <table class="table overflow">
        <tr>
          <th>Directory Name and ID</th>

          <th>Domains</th>
          <th>State</th>
          <th>Users</th>
          <th>Groups</th>
          <th>Delete</th>
        </tr>
        @for(var directory : directories.data)
          <tr>
            <td>${directory.name}<br><code>${directory.id}</code></td>
            <td>${directory.domain}</td>
            <td>${directory.state}</td>
            <td>
              <a href="/directories/${directory.id}/users">
                <button class="button directory_button">Users</button>
              </a>
            </td>
            <td>
              <a href="/directories/${directory.id}/groups">
                <button class="button directory_button">Groups</button>
              </a>
            </td>
            <td>
              <button class="button delete_button" onclick="deleteDirectory('${directory.id}', '${directory.name}')">Delete</button>
            </td>
          </tr>
        @endfor
      </table>
      @if(after != null)
        <a class="button directory_button" href="/directories?after=${after}">Before</a>
      @endif
      @if(before != null)
        <a class="button directory_button" href="/directories?before=${before}">Next</a>
      @endif
    </div>
  </div>
</div>

@param com.workos.directorysync.models.DirectoryList directories
@param String deleteResult

!{var before = directories.listMetadata.before;}
!{var after = directories.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">

<body>
<div class="logged_in_nav">
  <div class="flex">
    <div>
      <img class="nav-image" src="/images/workos-logo-with-text.png" alt="workos logo">
    </div>
  </div>
  <div>
    <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
    <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
        Reference</button></a>
    <a href="https://workos.com/blog" target="_blank"><button
              class='button nav-item blog-nav-button'>Blog</button></a>
    <a href="https://workos.com/" target="_blank"><button class='button button-outline'>WorkOS</button></a>
  </div>
</div>
<div class='flex_column  height-70vh'>
  <div class='flex_column width-70vw'>
    <div>
      <div class="flex_column directory_container">
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
        <table>
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
              <td style="text-align:left">${directory.name}<br><code>${directory.id}</code></td>
              <td>${directory.domain}</td>
              <td>${directory.state}</td>
              <td><a class="icon-button" href="/directories/${directory.id}/users">
                  <img class="org-icon" src="/images/icon-user.svg" alt="link to users">
                </a></td>
              <td><a class="icon-button" href="/directories/${directory.id}/groups">
                  <img class="org-icon" src="/images/icon-group.svg" alt="link to groups">
                </a>
              </td>
              <td><a class="icon-button" onclick="deleteDirectory('${directory.id}', '${directory.name}')">
                  <img class="org-icon" src="/images/icon-delete.svg" alt="delete group">
                </a>
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
      <a class="button button-outline" href="/">Back</a>

    </div>
    <div class="flex flex-end width-65vw">
      @if(after != null)
        <a class="button" href="?after=${after}">Before</a>
      @endif
      @if(before != null)
        <a class="button" href="?before=${before}">Next</a>
      @endif
    </div>
  </div>
</div>



<script>
  function deleteDirectory(directoryId, name) {
    if(confirm("Are you sure you want to delete directory '" + name + "'?")) {
      window.location.href="/directories/delete/" + directoryId;
    }
  }
</script>
</body>
@param com.workos.directorysync.models.DirectoryGroupList directoryGroups
@param String directoryId

!{var before = directoryGroups.listMetadata.before;}
!{var after = directoryGroups.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../main.css">

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
<div class='flex'>

  <div class="logged_in_div_right">
    <div class="flex_column directory_container">
      <table class="overflow">
        <tr>
          <th>Directory Group ID</th>
          <th>Directory Id</th>
          <th>Group Name</th>
        </tr>
        @for(var directoryGroup : directoryGroups.data)
          <tr>
            <td style="text-align: left"><a href="/directories/${directoryId}/groups/${directoryGroup.id}">${directoryGroup.id}</a></td>
            <td>${directoryGroup.directoryId}</td>
            <td>${directoryGroup.name}</td>
          </tr>
        @endfor
      </table>
        @if(after != null)
          <a class="btn btn-primary" href="/directories/${directoryId}/groups?after=${after}">Before</a>
        @endif
        @if(before != null)
          <a class="btn btn-primary" href="/directories/${directoryId}/groups?before=${before}">Next</a>
        @endif
      </div>
      <div class="flex flex_right directory_container">
        <div>
          <a class="button" href="/directories">Back to Directories</a>
        </div>
      </div>
    </div>
  </div>
</div>
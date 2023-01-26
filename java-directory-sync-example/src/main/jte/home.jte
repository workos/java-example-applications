@param com.workos.organizations.models.OrganizationList organizations

!{var before = organizations.listMetadata.before;}
!{var after = organizations.listMetadata.after;}

<link rel="stylesheet" href="main.css">

<body>
<div class="logged_in_nav">
  <div class="flex space-between width-100vw">
    <div><p>Java Directory Sync Example App</p></div>
    <div class="flex">
      <div>
        <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
      </div>
      <div>
        <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
            Reference</button></a>
      </div>
      <div>
        <a href="https://workos.com/blog" target="_blank"><button
                  class='button nav-item blog-nav-button'>Blog</button></a>
      </div>
      <div>
        <a href="https://workos.com/" target="_blank">
          <img class="nav-image" src="/images/workos_favicon.png" alt="link to workos.com">
        </a>
      </div>
    </div>

  </div>
</div>
<div class='flex_column  height-70vh'>
  <div class="flex width-70vw space-between">
    <div>
      <h2>Select a Directory</h2>
    </div>
  </div>

  <div class='flex_column card width-65vw'>
    <div>
      <table class="width-65vw">
        <tr>
          <th>Organization</th>
          <th>ID</th>
          <th>View Settings</th>
        </tr>
        @for(var organization : organizations.data)
          <tr>
            <td>${organization.name}</td>
            <td>${organization.id}</td>
            <td><a class="button button-outline" href="/directories?id=${organization.id}">fart</a></td>
          </tr>
        @endfor

      </table>
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
</body>
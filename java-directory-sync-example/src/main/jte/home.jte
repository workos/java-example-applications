@param com.workos.organizations.models.OrganizationList organizations

!{var before = organizations.listMetadata.before;}
!{var after = organizations.listMetadata.after;}

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
  <div class="flex width-70vw space-between">
    <div>
      <h2>Select an Organization</h2>
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
            <td><a class="icon-button" href="/directories?id=${organization.id}">
                <img class="org-icon" src="/images/icon-settings.svg" alt="link to workos.com">
              </a></td>
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
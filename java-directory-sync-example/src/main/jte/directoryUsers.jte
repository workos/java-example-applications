@param com.workos.directorysync.models.DirectoryUserList directoryUsers
@param String directoryId

!{var before = directoryUsers.listMetadata.before;}
!{var after = directoryUsers.listMetadata.after;}

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../main.css">

<div class="logged_in_nav">
  <div class="flex">
    <div>
      <img src="/images/workos-logo-with-text.png" alt="workos logo">
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
  <div class="logged_in_div_left">
    <div class="title-text">
      <h1>Your app,</h1>
      <h2 class="home-hero-gradient">Enterprise Ready</h2>
    </div>
    <div class="title-subtext">
      <p>Start selling to enterprise customers with just a few lines of code.</p>
      <p>Implement features like single sign-on in minutes instead of months.</p>
    </div>
    <div class="flex success-buttons">
      <a href="https://workos.com/signup" target="_blank"><button class='button'>Get Started</button></a>
      <a href="mailto:sales@workos.com?subject=WorkOS Sales Inquiry" target="_blank"><button
                class='button button-outline sales-button'>Contact
          Sales</button></a>
    </div>
  </div>
  <div class="logged_in_div_right">
    <div class="flex_column directory_container">
      <div class="container" style="padding: 40px;">
        <h3>Directory Users for <code>${directoryId}</code></h3>
        <table class="table">
          <tr>
            <th>Directory User ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>User State</th>
            <th>Groups</th>
          </tr>
          @for(var directoryUser : directoryUsers.data)
            <tr>
              <td><a href="/directories/${directoryId}/users/${directoryUser.id}">${directoryUser.id}</a></td>
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
        <div class="flex" style="justify-content: space-between">
          <div>
            @if(after != null)
              <a class="button" href="/directories/${directoryId}/users?after=${after}">Before</a>
            @endif
            @if(before != null)
              <a class="button" href="/directories/${directoryId}/users?before=${before}">Next</a>
            @endif
          </div>
          <div>
            <a class="button" href="/directories">Back to Directories</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

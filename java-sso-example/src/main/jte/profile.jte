@param com.workos.sso.models.Profile profile

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">

<div class="logged_in_nav">
  <div class="flex">
    <div>
      <img src="images/workos-logo-with-text.png" alt="workos logo">
    </div>

  </div>
  <div class="flex">
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
    <div class="flex_column">
      <table>
        <tr>
          <th>SSO Profile</th>
        </tr>
        <tr>
          <td>First Name: <code>${profile.firstName}</code></td>
        </tr>
        <tr>
          <td>Last Name: <code>${profile.lastName}</code></td>
        </tr>
        <tr>
          <td>Email: <code>${profile.email}</code></td>
        </tr>
        <tr>
          <td>ID: <code>${profile.id}</code></td>
        </tr>
        <tr>
          <td>Connection Type: <code>${profile.connectionType}</code></td>
        </tr>
      </table>
      <div class="flex width-40vw">
        <a href="/logout"><button class='button'>Logout</button></a>
      </div>
    </div>

  </div>
</div>

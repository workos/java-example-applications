@param com.workos.sso.models.Profile profile

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">

<div class="logged_in_nav">
  <div class="flex_column">
    <p>You're logged in ${profile.firstName}, welcome!</p>
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
      <a href="/"><button class='button'>Back</button></a>
    </div>
  </div>
  <div class="logged_in_div_right">
    <div class="flex_column">
      <h1>SSO Profile</h1>
      <table class="table">
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Email</th>
          <th>ID</th>
          <th>Connection Type</th>
        </tr>
        <tr>
          <td>${profile.firstName}</td>
          <td>${profile.lastName}</td>
          <td>${profile.email}</td>
          <td>${profile.id}</td>
          <td>${profile.connectionType}</td>
        </tr>
      </table>
    </div>
  </div>
</div>

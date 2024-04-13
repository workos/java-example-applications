@param com.workos.usermanagement.models.User user
@param com.workos.usermanagement.models.Identity[] identities

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
          <th>User</th>
        </tr>
        <tr>
          <td>First Name: <code>${user.getFirstName()}</code></td>
        </tr>
        <tr>
          <td>Last Name: <code>${user.getLastName()}</code></td>
        </tr>
        <tr>
          <td>Email: <code>${user.getEmail()}</code></td>
        </tr>
        <tr>
          <td>ID: <code>${user.getId()}</code></td>
        </tr>
        <tr>
          <td>Email Verified: <code>${user.getEmailVerified()}</code></td>
        </tr>
        <tr>
          <td>Identities: <code>${java.util.Arrays.stream(identities).map(i -> i.toString()).toList().toString()}</code></td>
        </tr>
      </table>
      <div class="flex width-40vw">
        <a href="/logout"><button class='button'>Logout</button></a>
      </div>
    </div>
  </div>
</div>

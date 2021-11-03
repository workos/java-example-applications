@param com.workos.sso.models.Profile profile

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Java Magic Link Example</h1>

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

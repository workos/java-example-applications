<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Java Directory Sync Example</h1>

  <table class="table">
    <tr>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>ID</th>
      <th>Connection Type</th>
    </tr>
    <tr>
      <td>${profile.getFirstName()}</td>
      <td>${profile.getLastName()}</td>
      <td>${profile.getEmail()}</td>
      <td>${profile.getId()}</td>
      <td>${profile.getConnectionType()}</td>
    </tr>
  </table>
</div>

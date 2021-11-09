@param String directoryUser
@param String directoryId
@param Boolean hasError

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Directory User</h1>

  <a class="btn btn-link" href="/directories/${directoryId}/users">Back to Users</a>

  @if(hasError == true)
    <div class="alert alert-danger" >Error occurred fetching user</div>
  @endif
  <pre>
    ${directoryUser}
  </pre>
</div>

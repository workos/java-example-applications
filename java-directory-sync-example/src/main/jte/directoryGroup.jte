@param String directoryGroup
@param String directoryId
@param Boolean hasError


<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Directory User</h1>

  <a class="btn btn-link" href="/directories/${directoryId}/groups">Back to Groups</a>

  @if(hasError)
    <div class="alert alert-danger" >Error occurred fetching group</div>
  @endif
  <pre>
    ${directoryGroup}
  </pre>
</div>

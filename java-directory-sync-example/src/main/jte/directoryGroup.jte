@param String directoryGroup
@param String directoryId
@param Boolean hasError

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../../main.css">


<div class="logged_in_nav">
  <div class="flex_column">
    <p>Group for Directory: <code>${directoryId}</code></p>
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
      <a href="/"><button class='button'>Home</button></a>
    </div>
  </div>
  <div class="logged_in_div_right">
    <div class="flex_column directory_container">
      <div class="container" style="padding: 40px;">
        <h1>Directory Group</h1>
        @if(hasError)
          <div class="alert alert-danger" >Error occurred fetching group</div>
        @endif
        <pre class="overflow">
          ${directoryGroup}
        </pre>
      </div>
      <div class="flex flex_right directory_container">
        <div>
          <a class="button" href="/directories/${directoryId}/groups">Back to Groups</a>
        </div>
      </div>
    </div>
  </div>
</div>

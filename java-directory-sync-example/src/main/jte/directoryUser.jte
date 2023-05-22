@param String directoryUser
@param String directoryId
@param Boolean hasError

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../../main.css">

<div class="logged_in_nav">
  <div class="flex space-between width-100vw">
    <div><p>Java Directory Sync Example App</p></div>
    <div class="flex">
      <div>
        <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
      </div>
      <div>
        <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
            Reference</button></a>
      </div>
      <div>
        <a href="https://workos.com/blog" target="_blank"><button
                  class='button nav-item blog-nav-button'>Blog</button></a>
      </div>
      <div>
        <a href="https://workos.com/" target="_blank">
          <img class="nav-image" src="/images/workos_favicon.png" alt="link to workos.com">
        </a>
      </div>
    </div>
  </div>
</div>
<div class='flex'>
  <div class="logged_in_div_right">
    <div class="flex_column directory_container">
      <h1>Directory User</h1>
      <div class="container text_box" style="padding: 40px;">
        @if(hasError == true)
          <div class="alert alert-danger" >Error occurred fetching user</div>
        @endif
        <pre id="noborder" class="overflow prettyprint">
          ${directoryUser}
        </pre>
      </div>
      <div class="flex" style="justify-content: right">
        <div>
          <a class="button" href="/directories/${directoryId}/users">Back to Users</a>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/gh/google/code-prettify@master/loader/run_prettify.js"></script>




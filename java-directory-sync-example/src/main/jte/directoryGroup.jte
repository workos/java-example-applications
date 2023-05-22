@param String directoryGroup
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
      <div class="container">
        <h1 style="padding: 40px;">Directory Group</h1>
        <div class="text_box">
          @if(hasError)
            <div class="alert alert-danger" >Error occurred fetching group</div>
          @endif
          <pre id="noborder" class="overflow prettyprint">
          ${directoryGroup}
        </pre>
        </div>
      </div>
      <div class="flex flex_right directory_container">
        <div>
          <a class="button" href="/directories/${directoryId}/groups">Back to Groups</a>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/gh/google/code-prettify@master/loader/run_prettify.js"></script>


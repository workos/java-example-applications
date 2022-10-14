@param String org_name
@param String org_id

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../main.css">

<div class="logged_in_nav">
  <div class="flex">
    <div>
      <img src="/images/workos-logo-with-text.png" alt="workos logo">
    </div>
  </div>
  <div>
    <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
    <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
        Reference</button></a>
    <a href="https://workos.com/blog" target="_blank"><button
              class='button nav-item blog-nav-button'>Blog</button></a>
    <a href="https://workos.com/" target="_blank"><button class='button button-outline'>WorkOS</button></a>
  </div>
</div>
<div class='flex'>
  <div class="logged_in_div_left">
    <div class="title-text">
      <h1>Your app,</h1>
      <h2 class="home-hero-gradient">Enterprise Ready</h2>
    </div>
    <div class="title-subtext">
      <p>Start selling to enterprise customers with just a few lines of code.</p>
      <p>Implement features like single sign-on in minutes instead of months.</p>
    </div>
    <div class="flex success-buttons">
      <a href="https://workos.com/signup" target="_blank"><button class='button'>Get Started</button></a>
      <a href="mailto:sales@workos.com?subject=WorkOS Sales Inquiry" target="_blank"><button
                class='button button-outline sales-button'>Contact
          Sales</button></a>
    </div>
  </div>
  <div class="logged_in_div_right">
    <div class="flex flex-start width-40vw space-between">
      <div><h1>${org_name}</h1></div>
      <div>
        <code class="org-id">${org_id}</code>
      </div>
    </div>
    <div class="flex flex-column card width-40vw ">
      <div class="org-button-div flex space-evenly width-40vw">
        <a href="/logout"><button class="button width-15vw">Change Org</button></a>
        <a href="/"><button class="button width-15vw">Go Back</button></a>
      </div>
    </div>

    <form action="/get_events" method="POST">
      <div class="flex card width-40vw">
        <div class="flex">
          <button class="box" name="event" id="generate_csv" value="0" type="submit" onclick="showSnackbar()">
            <div class="flex width-100p">
              <p>Generate CSV</p>
            </div>
          </button>
          <button class="box" name="event" id="access_csv" value="1" type="submit">
            <div class="flex width-100p">
              <p>Access CSV</p>
            </div>
          </button>
        </div>
      </div>
    </form>
    <div id="snackbar">CSV Generated</div>
  </div>
</div>
<script>
        function showSnackbar() {
            var x = document.getElementById("snackbar");
            x.className = "show";
            setTimeout(function(){ x.className = x.className.replace("show", ""); }, 500);
        }
</script>
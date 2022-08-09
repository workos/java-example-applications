@param com.workos.sso.models.Profile profile
@param String state

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../main.css">

<div class="logged_in_nav">
  <div class="flex">
    <div>
      <img src="images/workos-logo-with-text.png" alt="workos logo">
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
    <div class="flex_column directory_container">

      <h1>Java Magic Link Example</h1>

      @if(state != null)
        <div class="flex">
          <div class="margin_right">
            <p>Your state param: <code>${state}</code></p>
          </div>
        </div>
      @endif

      <table class="table">
        <tr>
          <th>Email</th>
          <th>ID</th>
          <th>Connection Type</th>
        </tr>
        <tr>
          <td>${profile.email}</td>
          <td>${profile.id}</td>
          <td>${profile.connectionType}</td>
        </tr>
      </table>
      <div class="flex flex_right directory_container">
          <a class="button" href="/">Back to home</a>
      </div>
    </div>
  </div>
</div>
</div>


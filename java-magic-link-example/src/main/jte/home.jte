<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">


<body class="container_login">
<div class='flex_column'>
  <div class="flex heading_div">
    <img src="images/workos_logo.png" alt="workos logo">
    <div class="heading_text_div">
      <div>
        <h1>WorkOS</h1>
      </div>
    </div>
  </div>
  <h2 class="bottom_margin_sm">Java Magic Link Example</h2>
  <form action="/send-magic-link" method="post">
    <div class="mb-3 form-group row">
      <input class="form-control" id="email" type="email" name="email" aria-describedby="emailHelp" placeholder="Enter Email to receive Magic Link"/>
    </div>
    <button type="submit" class="button login_button">Submit</button>
    <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
  </form>

</div>
</body>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">


<body class="container_login">
<div class='flex_column'>
  <div class="flex heading_div">
    <img src="images/workos-logo-with-text.png" alt="workos logo">
  </div>
  <h2 class="bottom_margin_sm">Java Magic Link Example</h2>
  <form action="/send-magic-link" method="post">
    <div class='flex_column'>
      <div>
        <input type="text" id="email" name="email" class="text_input">
      </div>
      <div>
        <button type="submit" class="button">Submit Email for Magic Link</button>
      </div>
    </div>
    <div id="emailHelp" class="form-text flex">We'll never share your email with anyone else.</div>
  </form>

</div>
</body>
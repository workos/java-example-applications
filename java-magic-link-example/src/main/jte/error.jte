@param String errorMessage

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Java Magic Link Example</h1>

  <div class="alert alert-danger" role="alert">There was an error creating your magic link session: ${errorMessage.isEmpty() ? "unknown error" : errorMessage}.</div>
</div>

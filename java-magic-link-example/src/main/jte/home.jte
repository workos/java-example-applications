<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Java Magic Link Example</h1>

  <form action="/send-magic-link" method="post">
    <div class="mb-3">
      <label class="form-label" for="email">Email</label>
      <input class="form-control" id="email" type="email" name="email" aria-describedby="emailHelp" />
      <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>

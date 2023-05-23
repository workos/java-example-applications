<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">


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
  <div class="logged_in_div_right">
    <div class="flex_column directory_container">
      <div class="flex flex_column">
        <div class="flex width-40vw space-between">
          <h2 class="lh-none">Live Webhooks View</h2>
          <div id="tutorial_button">
            <a href="https://workos.com/blog/test-workos-webhooks-locally-ngrok" target="_blank"
               class="button mt-15">Tutorial</a>
            <a href="/webhooks" class="button mt-10">Clear</a>
          </div>
        </div>
        <div id="webhooks-view"></div>
      </div>
    </div>
  </div>
</div>


<script>
  var webhooksView = document.getElementById('webhooks-view');

  function setupWebSocket() {
    var ws = new WebSocket('ws://localhost:7001/webhooks-ws');
    ws.onmessage = addWebhook;
  }

  function addWebhook(webhook) {
    webhooksView.classList.add("text_box");
    webhooksView.insertAdjacentHTML("afterbegin",
    "<div> Webhook received at:" + new Date().toISOString() + "</div>" +
    "<pre>" + webhook.data + "</pre>" + "<<hr>"
    );
  }

  setupWebSocket();
</script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<div class="container" style="padding: 40px;">
  <h1>Java Webhooks Example</h1>
  <a href="/" class="btn btn-primary">Clear</a>
  <div style="margin-top: 20px" id="webhooks-view"></div>
</div>

<script>
  var webhooksView = document.getElementById('webhooks-view');

  function setupWebSocket() {
    var ws = new WebSocket('ws://localhost:7001/webhooks-ws');
    ws.onmessage = addWebhook;
  }

  function addWebhook(webhook) {
    webhooksView.insertAdjacentHTML("beforeend",
    "<div> Webhook received at:" + new Date().toISOString() + "</div>" +
    "<pre>" + webhook.data + "</pre>"
    );
  }

  setupWebSocket();
</script>
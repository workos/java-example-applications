<head>
  <link rel="stylesheet" href="main.css" />
</head>
<body class="container_success">
<div class="logged_in_nav">
  <div class="flex">
    <div>
      <img
              src="images/workos-logo-with-text.png"
              alt="workos logo"
      />
    </div>
  </div>
  <div>
    <a href="https://workos.com/docs" target="_blank"
    ><button class="button nav-item">Documentation</button></a
    >
    <a href="https://workos.com/docs/reference" target="_blank"
    ><button class="button nav-item">API Reference</button></a
    >
    <a href="https://workos.com/blog" target="_blank"
    ><button class="button nav-item blog-nav-button">Blog</button></a
    >
    <a href="https://workos.com/" target="_blank"
    ><button class="button button-outline">WorkOS</button></a
    >
  </div>
</div>
<div class="flex">
  <div class="logged_in_div_right">
    <div class="flex_column">
      <h2>Which Admin Portal would you like to launch?</h2>
      <div class="flex">
        <table class="width-65vw">
          <tr>
            <th>Intent</th>
            <th>Create New Session</th>
          </tr>
          <tr>
            <td class="ta-left">SSO</td>
            <td>
              <a
                      class="button button-outline"
                      href="/admin-portal/generateLink?intent=sso"
              ><img class="org-icon" src="/images/icon-settings.svg" alt="link to workos.com"></a
              >
            </td>
          </tr>
          <tr>
            <td class="ta-left">Directory Sync</td>
            <td>
              <a
                      class="button button-outline"
                      href="/admin-portal/generateLink?intent=dsync"
              ><img class="org-icon" src="/images/icon-settings.svg" alt="link to workos.com"></a
              >
            </td>
          </tr>
          <tr>
            <td class="ta-left">Audit Logs</td>
            <td>
              <a
                      class="button button-outline"
                      href="/admin-portal/generateLink?intent=audit_logs"
              ><img class="org-icon" src="/images/icon-settings.svg" alt="link to workos.com"></a
              >
            </td>
          </tr>
          <tr>
            <td class="ta-left">Log Streams</td>
            <td>
              <a
                      class="button button-outline"
                      href="/admin-portal/generateLink?intent=log_streams"
              ><img class="org-icon" src="/images/icon-settings.svg" alt="link to workos.com"></a
              >
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>

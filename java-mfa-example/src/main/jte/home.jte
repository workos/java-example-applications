@param java.util.ArrayList<String> factorIdList

<link rel="stylesheet" href="main.css">

<body class="container_success">
<div class="logged_in_nav">
  <div class="flex">
    <p>MFA Example Application</p>
  </div>
  <div>
    <img src="images/workos_logo.png" alt="workos logo">
  </div>
</div>
<div class='flex'>
  <div class="logged_in_div_left">
    <div>
      <h1>Your app,</h1>
      <h2>Enterprise Ready</h2>
    </div>
    <div>
      <a href="https://workos.com/" target="_blank"><button class='button'>WorkOS</button></a>
      <a href="https://workos.com/docs" target="_blank"><button class='button'>Documentation</button></a>
      <a href="https://workos.com/docs/reference" target="_blank"><button class='button'>API
          Reference</button></a>
      <a href="https://workos.com/blog" target="_blank"><button class='button'>Blog</button></a>

    </div>
  </div>
  <div class="logged_in_div_right">
    <div class="flex_column">

      <div>
        <div class="flex_column">
          <div class="flex space_between width_40vw">
            <div>
              <h2>Current MFA Factors</h2>
            </div>

            <div class="flex space-between">
              <a href="/enroll_factor_details">
                <button class="button button-outline button-sm">Enroll New Factor</button>
              </a>
              <a href="/clear_session">
                <button class="button button-outline button-sm">Clear Factors</button>
              </a>
            </div>
          </div>
        </div>

          @if(factorIdList != null)
            <div class="flex_column">
              @for(var factor : factorIdList)
                <a href="/factor_detail?id=${factor}">
                  <button class="button login_button">${factor}</button>
                </a>
              @endfor
            </divclass>

            @else
              <div class="mt-20 card width_40vw">
                <p style="text-align: center; color: gray;">No Current Factors</p>
              </div>
          @endif

      </div>
    </div>

  </div>
</div>

</body>


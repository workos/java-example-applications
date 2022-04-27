<<<<<<< HEAD
<%--@param com.workos.mfa.models.Factor factor--%>

<%--!{var type = factor.type;}--%>
=======
@param java.util.ArrayList<com.workos.mfa.models.Factor> factorList
>>>>>>> a88d3ab782a7475ee5659b72a239284612f85c15

<link rel="stylesheet" href="main.css">

<body class="container_success">
<<<<<<< HEAD
    <div class='logged_in_nav'>
        <div class='flex'>
            <h3>MFA Example App</h3>
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
                <a href="https://workos.com/docs/reference" target="_blank"><button class='button'>API Reference</button></a>
                <a href="https://workos.com/blog" target="_blank"><button class='button'>Blog</button></a>

            </div>
        </div>

    <div class="logged_in_div_right">
        <div class="flex_column">

            <div class="flex">
                <div class="factor_card flex_column space-evenly">
                    <form method="POST" action="enroll_factor">
                        <div>
                            <div class="flex">
                                <input class="text_input" type="text" id="phone_number" name="phone_number"
                                       placeholder="Phone Number" required>
                            </div>
                            <div class="flex">
                                <button type="submit" name="type" value="sms"
                                        class="button button-outline button-sm">Enroll New
                                    SMS
                                    Factor</button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="factor_card flex_column space-evenly">
                    <form method="POST" action="enroll_factor">
                        <div class="flex-column">
                            <div class="flex">
                                <input class="text_input" type="text" id="totp_issuer" name="totp_issuer"
                                       placeholder="TOTP Issuer" required>
                            </div>
                            <div class="flex">
                                <input class="text_input" type="text" id="totp_user" name="totp_user"
                                       placeholder="User Email" required>
                            </div>
                        </div>
                        <div class="flex">
                            <button type="submit" name="type" value="totp"
                                    class="button button-outline button-sm">Enroll
                                New TOTP Factor</button>
                        </div>
                    </form>
                </div>
            </div>
                <h2>MFA Factors List</h2>
            <h2>(DISPLAY FACTORS HERE)</h2>

            </div>
        </div>
    </div>
=======
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
      <div class="flex space_between ">
        <div class="factor_card">
          <h2>Enroll SMS Factor</h2>
          <form action="/enroll_factor" method="post">
            <div class="flex">
              <div class="flex">
                <input class="text_input" type="text" id="phone_number" name="phone_number"
                       placeholder="Phone Number" required>
              </div>
            </div>
            <div>
              <button type="submit" name="type" value="sms"
                      class="button button-outline button-sm">Enroll New
                Factor</button>
            </div>
          </form>
        </div>

        <div class="factor_card">
          <h2>Enroll TOTP Factor</h2>
          <form method="POST" action="/enroll_factor">
            <div class="flex-column">
              <div class="flex">
                <input class="text_input" type="text" id="totp_issuer" name="totp_issuer"
                       placeholder="TOTP Issuer" required>
              </div>
              <div class="flex">
                <input class="text_input" type="text" id="totp_user" name="totp_user"
                       placeholder="User Email" required>
              </div>
            </div>
            <div>
              <button type="submit" name="type" value="totp"
                      class="button button-outline button-sm">Enroll New Factor</button>
            </div>
          </form>
        </div>
      </div>
      <div>
        <h1>List of Current Factors</h1>


          @if(factorList != null)
          @for(var factor : factorList)
            <button>${factor.id}</button>
          @endfor
          @endif


      </div>
    </div>

  </div>
</div>

>>>>>>> a88d3ab782a7475ee5659b72a239284612f85c15
</body>

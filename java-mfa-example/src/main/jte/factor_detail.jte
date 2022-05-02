@param String factorId
@param String type
@param String phoneNumber
@param String createdAt
@param String qrCode



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
            <div class="flex">
                <div class="flex-column">
                    <div>
                        <h2>ID: ${factorId}</h2>
                    </div>
                    <div>
                        <p>Type: <code>${type}</code></p>
                        @if(type.equals("sms"))
                            <p>Phone Number: <code>${phoneNumber}</code></p>
                        @endif
                        <p>Created At: <code>${createdAt}</code></p>
                    </div>
                </div>
                @if(type.equals("totp"))
                    <div class="qr_div">
                      <img class="qr_code" src="${qrCode}" alt="qr_code">
                    </div>
                @endif

            </div>

            <div class="flex-column">
                <div>
                    <form method="POST" action="/challenge_factor">
                        <div class='flex_column'>
                            @if(type.equals("sms"))
                                <div>
                                    <input type="text" id="sms_message" name="sms_message" class="text_input"
                                           placeholder="Custom SMS Message Input {{code}}">
                                    <input hidden type="text" id="factorId" name="factorId" value="${factorId}">
                                </div>
                            @endif

                            <div>
                                <button type="submit" class="button" name="type" value="${type}">Challenge Factor</button>
                                <a href="/" class="button">Home</a>
                            </div>
                        </div>
                    </form>

                </div>
            </div>

        </div>

    </div>
</div>
</body>

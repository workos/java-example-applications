@param String factorId
@param String type
@param String phoneNumber
@param String createdAt
@param String qrCode



<link rel="stylesheet" href="main.css">

<body class="container_success">
<div class="logged_in_nav">
    <div class="flex">
        <div>
            <img src="images/workos-logo-with-text.png" alt="workos logo">
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
    <div class="logged_in_div_left">
        <div class="title-text">
            <h1>Your app,</h1>
            <h2 class="home-hero-gradient">Enterprise Ready</h2>
        </div>
        <div class="title-subtext">
            <p>Start selling to enterprise customers with just a few lines of code.</p>
            <p>Implement features like single sign-on in minutes instead of months.</p>
        </div>
        <div class="flex success-buttons">
            <a href="https://workos.com/signup" target="_blank"><button class='button'>Get Started</button></a>
            <a href="mailto:sales@workos.com?subject=WorkOS Sales Inquiry" target="_blank"><button
                        class='button button-outline sales-button'>Contact
                    Sales</button></a>
        </div>
    </div>
    <div class="logged_in_div_right">
        <div class="flex_column">
            <div class="flex">
                <div class="flex-column height-215">
                    <div>
                        <h2>ID: ${factorId}</h2>
                    </div>
                    <div class="flex space-between">
                        <div>
                            <p>Type: <code>${type}</code></p>
                            @if(type.equals("sms"))
                                <p>Phone Number: <code>${phoneNumber}</code></p>
                            @endif
                            <p>Created At: <code>${createdAt}</code></p>
                        </div>

                        @if(type.equals("totp"))
                            <div class="qr_div">
                                <img class="qr_code" src="${qrCode}" alt="qr_code">
                            </div>
                        @endif
                    </div>
                </div>



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
                                <a href="/" class="button button-outline">Home</a>
                            </div>
                        </div>
                    </form>

                </div>
            </div>

        </div>

    </div>
</div>
</body>

@param String factorId
@param String createdAt
@param String expiresAt
@param String valid
@param String type

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
            <div class="flex-column">
                <div class="card">
                    <h1>User Verified: ${valid}</h1>
                    <p>Factor ID: ${factorId}</p>
                    <p>Created At: ${createdAt}</p>
                    @if(type.equals("sms"))
                        <p>Expires At: ${expiresAt}</p>
                    @endif
                </div>
            </div>
            <a href="/"><button class='button'>Go Back Home</button></a>
        </div>

    </div>
</div>
</body>

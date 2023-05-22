@param String factorId
@param String createdAt
@param String expiresAt
@param String valid
@param String type

<link rel="stylesheet" href="main.css">

<body class="container_success">
<div class="logged_in_nav">
    <div class="flex">
        <div>
            <img src="images/workos-logo-with-text.png" alt="workos logo">
        </div>
    </div>
    <div class="flex">
        <div>
            <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
        </div>
        <div>
            <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
                    Reference</button></a>
        </div>
        <div>
            <a href="https://workos.com/blog" target="_blank"><button
                        class='button nav-item blog-nav-button'>Blog</button></a>
        </div>
        <a href="https://workos.com/" target="_blank">
            <button class='button button-outline'>WorkOS</button>
        </a>
    </div>
</div>
<div class='flex'>
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
            <a href="/">
                <button class='button'>Go Back Home</button>
            </a>
        </div>

    </div>
</div>
</body>

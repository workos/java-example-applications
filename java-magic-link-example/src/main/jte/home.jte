<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="main.css">


<body class="height-100vh">
<div class="logged_in_nav">
    <div class="flex">
        <div>
            <img
                    src="images/workos-logo-with-text.png"
                    alt="workos logo"
            />
        </div>
    </div>
    <div class="flex">
        <a href="https://workos.com/docs" target="_blank"
        >
            <button class="button nav-item">Documentation</button>
        </a
        >
        <a href="https://workos.com/docs/reference" target="_blank"
        >
            <button class="button nav-item">API Reference</button>
        </a
        >
        <a href="https://workos.com/blog" target="_blank"
        >
            <button class="button nav-item blog-nav-button">Blog</button>
        </a
        >
        <a href="https://workos.com/" target="_blank"
        >
            <button class="button button-outline">WorkOS</button>
        </a
        >
    </div>
</div>
<div class="flex flex_column height-80vh">
    <div class="flex height-40vh">
        <div class="card width-335">
            <div class="flex_column">
                <div>
                    <span>Log in with Magic Link</span>
                </div>
                <hr style="width: 100%; margin-top: 15px; margin-bottom: 20px"/>
                <form method="POST" action="/send-magic-link">
                    <div class="flex_column">
                        <div>
                            <input
                                    type="text"
                                    id="email"
                                    name="email"
                                    class="text_input width-225px"
                                    placeholder="Enter email"
                            />
                        </div>
                        <div>
                            <button type="submit" class="button width-225px">
                                Login
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body></body>
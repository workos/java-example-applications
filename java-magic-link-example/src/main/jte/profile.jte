@param com.workos.sso.models.Profile profile
@param String state

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="../../main.css">


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
<div class="flex">
    <div class="logged_in_div_right">
        <div class="flex_column">
            <h2>Login Successful</h2>
            <br>
            <div class="text_box">
                <table class="table">
                    <tr>
                        <th>Key</th>
                        <th>Value</th>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td>${profile.email}</td>
                    </tr>
                    <tr>
                        <td>User ID</td>
                        <td>${profile.id}</td>
                    </tr>
                    <tr>
                        <td>Connection Type</td>
                        <td>${profile.connectionType}</td>
                    </tr>
                    <tr>
                        <td>Connection ID</td>
                        <td>${profile.connectionId}</td>
                    </tr>
                    @if(state != null)
                        <tr>
                            <td>State</td>
                            <td>${state}</td>
                        </tr>
                    @endif
                </table>
            </div>
        </div>
    </div>
</div>
</body>

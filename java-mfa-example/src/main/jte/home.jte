@param java.util.ArrayList<String> $factorIdList
@param java.util.ArrayList<com.workos.mfa.models.Factor> factorList

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

            <div>
                <div class="flex_column">
                    <div class="flex space-between width-725px">
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

                @if(factorList != null)
                    <table class="width-725px">
                        <tr>
                            <th>Type</th>
                            <th>ID</th>
                            <th>View Details</th>
                        </tr>
                        @for(var factor : factorList)
                            <tr>
                                <td>${factor.type}</td>
                                <td>${factor.id}</td>
                                <td><a class="button button-outline" href="/factor_detail?id=${factor.id}"><img
                                                class="height-20px" src="/images/icon-settings.svg" alt="settings icon"></a>
                                </td>
                            </tr>
                        @endfor
                    </table>

                @else
                    <div class="mt-20 card width-725px">
                        <p style="text-align: center; color: gray;">No Current Factors</p>
                    </div>
                @endif


            </div>
        </div>

    </div>
</div>

</body>


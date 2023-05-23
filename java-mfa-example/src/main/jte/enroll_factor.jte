<link rel="stylesheet" href="main.css">

<head>
    <link rel="stylesheet" href="main.css">
</head>

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
            <div class="flex space_between ">
                <div class="factor_card height-250px">
                    <h2>Enroll SMS Factor</h2>
                    <form action="/enroll_sms_factor" method="post">
                        <div class="flex flex_column">
                            <div class="flex">
                                <input class="text_input" type="text" id="phone_number" name="phone_number"
                                       placeholder="Phone Number">
                            </div>
                            <div>
                                <button type="submit" name="type" value="sms"
                                        class="button button-outline button-sm" id="sms-factor-submit-btn"
                                        disabled>Enroll New Factor
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="factor_card height-250px">
                    <h2>Enroll TOTP Factor</h2>
                    <div>
                        <div class="flex_column">
                            <div class="flex">
                                <input class="text_input" type="text" id="totp_issuer" name="totp_issuer"
                                       placeholder="TOTP Issuer">
                            </div>
                            <div class="flex">
                                <input class="text_input" type="text" id="totp_user" name="totp_user"
                                       placeholder="User Email">
                            </div>

                            <div>
                                <button type="submit" name="type" value="totp"
                                        class="button button-outline button-sm" id="totp-factor-submit-btn"
                                        disabled>Enroll New Factor
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="overlay" id="overlay" style="display: none;">
    <div class="modal" id="modal">
        <button id="close-modal-btn">I've scanned a QR code</button>
    </div>
</div>
</body>

<script>
    const phoneNumber = document.getElementById("phone_number")
    const totpIssuer = document.getElementById("totp_issuer")
    const totpUser = document.getElementById("totp_user")
    const smsSubmitButton = document.getElementById("sms-factor-submit-btn")
    const totpSubmitButton = document.getElementById("totp-factor-submit-btn")
    const closeModalBtn = document.getElementById("close-modal-btn")
    const overlay = document.getElementById("overlay")
    const modal = document.getElementById("modal")

    phoneNumber.addEventListener("input", validateSmsForm)
    totpIssuer.addEventListener("input", validateTotpForm)
    totpUser.addEventListener("input", validateTotpForm)

    function validateSmsForm() {
        if (phoneNumber.value.trim() !== "") {
            smsSubmitButton.disabled = false
        } else {
            smsSubmitButton.disabled = true
        }
    }

    function validateTotpForm() {
        if (totpIssuer.value.trim() !== "" && totpUser.value.trim() !== "") {
            totpSubmitButton.disabled = false
        } else {
            totpSubmitButton.disabled = true
        }
    }

    totpSubmitButton.addEventListener("click", function() {
        fetch('/enroll_totp_factor', {
            method: 'POST',
            headers: {
               'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                issuer: totpIssuer.value,
                user: totpUser.value,
            })
        })
            .then(response => response.json())
            .then(response => {
                overlay.style.display = "block"
                modal.innerHTML = `
                    <h2>Scan the QR code</h2>
                    <p class="qr_code_instructions">Use the authenticator app to scan the QR code. After you scan the code, click 'Continue'.</p>
                    <img class="qr_code" id="qr_code" src="" alt="qr_code">
                    <a href="/" class="button button-outline">Continue</a>
                `
                const qrCode = document.getElementById("qr_code");
                qrCode.src = response.totp.qr_code
            })
        })

    closeModalBtn.addEventListener("click", function() {
        overlay.style.display = "none"
    })

</script>

If your SaaS product’s backend is built with Java—or a JVM-compatible language such as Kotlin, Groovy, Scala, or Clojure—and you want to incorporate WorkOS’ Admin Portal functionality, you can do a dry-run of the Admin Portal integration using our [example Java app](https://github.com/workos/java-example-applications/tree/main/java-admin-portal-example). It makes use of the [WorkOS Kotlin SDK](https://github.com/workos/workos-kotlin).

If you get stuck while following the steps below and aren't able to resolve the issue by reading our [API reference](https://workos.com/docs/reference) or [Admin Portal Setup Guide](https://workos.com/docs/admin-portal/guide), please reach out to us at support@workos.com so we can help!

## Prerequisites
A free WorkOS account, and each of these installed on your machine:
- Java version 1.8+
- The Java Development Kit (JDK), which includes the Java Runtime Environment (JRE)

## Clone the Java app
1. In your CLI, navigate to the directory into which you want to clone the Java example app git repo:
```bash
$ cd ~/Desktop
```

2. Clone the main Java example app repo:
```bash
# HTTPS
$ git clone https://github.com/workos/java-example-applications.git

or

# SSH
$ git clone git@github.com:workos/java-example-applications.git
```

3. Navigate to the cloned repo:
```bash
$ cd java-example-applications/
```

## Securely store the environment variables
4. Obtain and make note of your WorkOS API key and WorkOS Client ID from the WorkOS Dashboard. The locations of these values are shown in the screenshots below.

![Screenshot of the WorkOS dashboard showing where to locate the API key](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/61986a545cae6987e741c044_TXlyTFBXjAfHZwhb9l-YRvpdj3LCCSXX5frveCFXh1Ywlc482yvdpKHDDRl9QKH3CXbsCwCj9Sya4DAmxvvK293sREyeTJJW8NidhsDgc5lXSU15H6cFpHIlXaAeqHXge259YQju.png)

![Screenshot of the WorkOS dashboard showing where to locate the Client ID](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/61986a53882d3a558ae819ee_-ZbW48EgfBtiMuTQEDAaV0UtSxw2wt6Mx-NAX5YxIdI87AZT3bI5w_7jS6tHk-TlG0aHC08AD-l_wr3v_RmUMzSyTehrLIk8D5A7hQ5UskvPVeuXec-9yf6pLTBxkm68PF3kHsqv.png)

5. Create a .env file in the java-example-applications/ directory to store the environment variables:
```bash
$ touch .env
```

6. Open the new .env file with your preferred text editor and replace the placeholder values for WORKOS_API_KEY and WORKOS_CLIENT_ID:
```bash
WORKOS_API_KEY=your_api_key_here
WORKOS_CLIENT_ID=your_project_id_here
```

The .env file is listed in this repo's .gitignore file, so your sensitive information will not be checked into version control. This is an important consideration for keeping sensitive information such as API keys private. The WorkOS Kotlin SDK will read your API key and Client ID from the .env file.

## Start an ngrok session
[Ngrok](https://ngrok.com/) is a tool that allows you to map a local endpoint (like localhost) to a public endpoint (like https://something.ngrok.io).

The cloned example app will run on http://localhost:7001. Ngrok will create a tunnel to the application so we can receive webhooks from WorkOS.

7. Start a new ngrok session by running this command from the java-example-applications/ directory:
```bash
$ ngrok http 7001
```
You’ll see output in the command line from ngrok. You’ll need to keep this CLI window open to persist the ngrok process. The two lines beginning with “Forwarding” in the output are important. The endpoints shown on those two lines with the random alphanumeric strings are the ngrok endpoints, and you’ll also see the localhost endpoints where your web server is running. Note that the only difference between the two “Forwarding” URLs is that one uses HTTP and the other uses HTTPS. For our example, you’ll need to use the HTTPS version which will be in the format of https://<random-alphanumeric-string>.ngrok.io.

You’ll need to keep the CLI tab that’s running ngrok open while you go through the rest of the tutorial. Open a new CLI tab for any other commands you need to run.

## Set Up a WorkOS Endpoint
8. Log into the [WorkOS Dashboard](https://dashboard.workos.com/webhooks) and navigate to the Webhooks section:

![A screenshot of the WorkOS dashboard](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/619b26da32209fd766e03b52_dMOc7tSsPDckED5NJt-dUbeP3gMO7sTaisKBJfsJ3pbX9UrhisE4X96ZNoWNcWHW3jDtX2s13ED-jJWB24j1kmfIt-pp9-1F0gxRuBRqxIqGwdGh3jK_YteZSk3OAfzvqvfv5O7u.png)

9. Click on the “Create Webhook” button shown in the screenshot above. A Modal will appear. Copy and paste the HTTPS endpoint shown in the ngrok output into the URL field shown in the modal, then append /webhooks to the end of ngrok endpoint. Select all the checkboxes for the purposes of this example, as shown in the screenshot below:
  
![A screenshot of the webhooks setup modal](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/619b26da265aac0db60de1b7_Z7-iG33Ol9G8fzkp00CWqsYZ-D9UhnYXu1-KcpQ-exaVkUE5ZsiyYUUa__qHJQ5k_jRLgTk2iAU96vDnRxtLlpskTE7xXWkJ-dNfpKhhDOggBQWaqAe8T6J3qIJA6MA8X_oqFu5Y.png)
  
The cloned application is listening for webhook requests at http://localhost:7001/webhooks
  
## Set Up Webhooks Secret
10. In order for the SDK to validate the WorkOS webhook, we’ll need to supply the webhook’s secret key. To find the secret key, first navigate to the new webhook’s detail page:

![A screenshot of the webhooks detail page](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/619b26dad3a10e2b4d3b1f85_g7YqmyF7D4W5vn_88Z8snQxjkbLPigVixEJK23fJqeNH7kap5W-GETJ87G0rF-0ZXM64wBidDOAVHPH4UcTOy_TbkwtqyVTIDyenXBoSYxotQzMvOiyqEYWbszyBYVCKGlUEcRmP.png)
  
11. Then locate and copy the secret key:
  
![A screenshot of the secret key in the webhooks dashboard](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/619b26da3e48cf178c6cbb54_iBwaVrYX5pWQr58QKuphkHeOwZzPDHUe-56Wzzp-mXz4_eGVQiGF5UbY7JQSlog2YkqP0lMYA6lbhm47CzcpdnAexoGAlspkb3iuu-Xd_9NW2Vk9zX-39QUgOVkSGUHUSAjj_QB7.png)
  
12. Open the .env file in your text editor again, and add this key-value pair beneath the WorkOS API key that’s already there:
```bash
WORKOS_WEBHOOK_SECRET=your_webhook_secret
```
  
## Test the integration
13. While keeping the ngrok process running in one CLI tab, open another CLI tab and start the server by running this command in the new tab:
```bash
$ ./gradlew :java-webhooks-example:run
```

14. Navigate to http://localhost:7001. You’ll see a simple webpage with a button that says “Clear”.

15. From the webhook’s detail page in the WorkOS dashboard, click the “Send test webhook” button:
  
![A screenshot of the "send test webhook" button](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/619b26da253a480b3edbb493_7qgsBjIN4Yq_wGzFSOdWBuKx51c0X8YnBcH23A0ZGaJ3wnQeeZcTTVsRJIYdHXVq7Nc8Yk1GjYoxdalFTvn5zmMLXo-vDycbynK0FnZx-Ktxaj-h1NuW3-Khtk17O8C_a7-DNDs3.png)
  
16. A modal will appear with a pre-selected payload. Click the “Send test webhook” button at the bottom of the modal.

17. Navigate back to http://localhost:7001. You’ll see the payload appear!

Nice work! You just set up WorkOS webhooks for your example Java app!

## Need help?
If you get stuck while following the tutorial and aren't able to resolve the issue by reading our [API reference for Webhooks](https://workos.com/docs/reference/webhooks/connection) or [our guide on testing WorkOS webhooks locally](https://workos.com/blog/test-workos-webhooks-locally-ngrok), please reach out to us at support@workos.com so we can help!

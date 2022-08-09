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

## Update the Admin Portal Redirect Link
7. After an Admin Portal user creates an SSO connection using the Admin Portal, they need to be redirected to a webpage within your application (usually this webpage confirms successful creation of the connection). To configure which webpage this is, enter the webpage’s URL in the Configuration section of your WorkOS dashboard under the “Admin Portal Redirect Link” header. For production usage this URL must begin with HTTPS, but for development purposes the URL can begin with HTTP.

    For this tutorial, update the Admin Portal Redirect Link at the bottom of the "Configuration" page of your WorkOS Dashboard to http://localhost:7001 as shown below:
![A screenshot showing where to change the Admin Portal redirect link](https://assets-global.website-files.com/5f03ef1d331a69193fae6dcd/619d4a5f678e55cfe270b5db_B0OROU-gS3Ud05iphmSJbEyhbMxAOzX-BL863R-rL4_gfUwKI9tqjUeKIjQINttkY_nnPJmEqL_hmpQO2oV5nZJmZcb4TiBLPoiZWMUGI4t7KEu6qLMdtOKBnIHurc3KmMc5Nsa8.png)

## Test the integration
8. Start the server by running this command in the CLI while you’re in the java-example-applications/ directory:
```bash
./gradlew :java-admin-portal-example:run
```

9. With the server running, navigate to http://localhost:7001/ to view the example Admin Portal.
10. Enter the name of the new Organization to be created and the names of all of the Organization's associated domains.
    - The Organization must be a new Organization that doesn't yet exist in your WorkOS dashboard
    - The domains should be entered as space-separated values, e.g. "domain1.com domain2.com domain3.com"

## Need help?
If you get stuck and aren't able to resolve the issue by reading our [WorkOS Admin Portal documentation](https://workos.com/docs/admin-portal/guide), [API reference](https://workos.com/docs/reference), or tutorials, you can reach out to us at support@workos.com and we'll lend a hand.

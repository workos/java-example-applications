If your SaaS product’s backend is built with Java—or a JVM-compatible language such as Kotlin, Groovy, Scala, or Clojure—and you want to incorporate WorkOS’ Audit Logs functionality, you can do a dry-run of the Audit Logs feature using our [example Java app](https://github.com/workos/java-example-applications/tree/main/java-audit-logs-example). It makes use of the [WorkOS Kotlin SDK](https://github.com/workos/workos-kotlin).

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

## Test the integration
7. Start the server by running this command in the CLI while you’re in the java-example-applications/ directory:
```bash
$ ./gradlew :java-audit-logs-example:run
```

8. Navigate to http://localhost:7001. Select an Organization.

## Audit Logs Setup with WorkOS

9. Follow the [Audit Logs configuration steps](https://workos.com/docs/audit-logs/emit-an-audit-log-event/sign-in-to-your-workos-dashboard-account-and-configure-audit-log-event-schemas) to set up the following 5 events that are sent with this example:

Action title: "user.organization_set" | Target type: "team"
Action title: "user.organization_deleted" | Target type: "team"



## Need help?
If you get stuck while following the steps below and aren't able to resolve the issue by reading our [API reference](https://workos.com/docs/reference) or [Directory Sync Setup Guide](https://workos.com/docs/directory-sync/guide), please reach out to us at support@workos.com so we can help!

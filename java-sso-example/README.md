# Java Example App with SSO powered by WorkOS

An example application demonstrating to use the [WorkOS Kotlin/Java SDK](https://github.com/workos-inc/workos-kotlin) to authenticate users via SSO.

## Node Project Setup

1. In your CLI, navigate to the directory into which you want to clone this git repo.

   ```bash
   $ cd ~/Desktop/
   ```

2. Clone the main repo and install dependencies for the app you'd like to use:

   ```bash
   # HTTPS
   git clone https://github.com/workos-inc/java-example-applications.git
   ```

   or

   ```bash
   # SSH
   git clone git@github.com:workos-inc/java-example-applications.git
   ```

## Configure your environment

1. Grab your [API Key](https://dashboard.workos.com/api-keys).
2. Get your [Client ID](https://dashboard.workos.com/configuration).
3. Create a `.env` file at the root of the project and populate with the
   following environment variables (using values found above):

```typescript
WORKOS_API_KEY=your_api_key_here;
WORKOS_CLIENT_ID=your_client_id_here;
```

4. Set your [Default Redirect Link](https://dashboard.workos.com/configuration) to `http://localhost:7004/callback`.

## Source your enivornment variables

```sh
source.env
```

## Run the server and log in using SSO

```sh
./gradlew :java-sso-example:run
```

Head to `http://localhost:7004/` to begin!

## Need help?

If you get stuck and aren't able to resolve the issue by reading our [WorkOS SSO documentation](https://workos.com/docs/sso/guide/introduction), API reference, or tutorials, you can reach out to us at support@workos.com and we'll lend a hand.

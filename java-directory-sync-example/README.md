# Java Example App with Directory Sync powered by WorkOS

An example application demonstrating to use the [WorkOS Kotlin/Java SDK](https://github.com/workos-inc/workos-kotlin) to power Directory Sync.

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
WORKOS_API_KEY = your_api_key_here;
WORKOS_CLIENT_ID = your_client_id_here;
```

## Run the server

```sh
./gradlew :java-directory-sync-example:run
```

Head to `http://localhost:7001/` to begin!

## Need help?

If you get stuck and aren't able to resolve the issue by reading our [WorkOS Directory Sync documentation](https://workos.com/docs/directory-sync/guide/introduction), API reference, or tutorials, you can reach out to us at support@workos.com and we'll lend a hand.

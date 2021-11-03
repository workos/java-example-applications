# WorkOS Webhooks Example Application

## Description

This is a simple web application that uses the WorkOS Java SDK to validate and display webhooks sent from WorkOS in real time.

## Running 

### 1. Set Up Local WorkOS API Key
Log into the [WorkOS Dashboard](https://dashboard.workos.com/api-keys) and set the `WORKOS_API_KEY` environment variable to your WorkOS API Key

```sh
export WORKOS_API_KEY={{YOUR_API_KEY}}
```

### 2. Start an `ngrok` session 

[Ngrok](https://ngrok.com/) is a simple application that allows you to map a local endpoint to a public endpoint.

The application will run on http://localhost:7005. Ngrok will create a tunnel to the application so we can receive webhooks from WorkOS.
```sh
./ngrok http 7005
```

### 3. Set Up a WorkOS Endpoint
Log into the [WorkOS Dashboard](https://dashboard.workos.com/webhooks) and add a Webhook endpoint with the public ngrok URL with `/webhooks` appended.

The local application is listening for webhook requests at http://localhost:7005/webhooks

### 4. Set Up Webhooks Secret
In order for the SDK to validate that WorkOS webhooks, set the Webhook secret from the dashboard into the `WORKOS_WEBHOOK_SECRET` environment variable.
```sh
export WORKOS_WEBHOOK_SECRET={{YOUR_WEBHOOK_SECRET}}
```

### 5. Run the sample application
This will run the application on http://localhost:7005. Visit this URL from your browser and test sending webhooks from the WorkOS dashboard. The local application should display received webhooks live.
```
./gradlew :java-webhooks-example:run
``` 



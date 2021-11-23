# WorkOS Webhooks Example Application

## Description

This is a simple web application that uses the WorkOS Java SDK to validate and display webhooks sent from WorkOS in real time.

## Running

### 1. Set Up Local WorkOS API Key

Log into the [WorkOS Dashboard](https://dashboard.workos.com/api-keys) and locate your WorkOS API Key.

Then create a `.env` file at the root of the project and populate the following environment variable.

```
WORKOS_API_KEY=your_api_key_here
```

### 2. Start an `ngrok` session

[Ngrok](https://ngrok.com/) is a simple application that allows you to map a local endpoint to a public endpoint.

The application will run on http://localhost:7001. Ngrok will create a tunnel to the application so we can receive webhooks from WorkOS.

```sh
./ngrok http 7001
```

### 3. Set Up a WorkOS Endpoint

Log into the [WorkOS Dashboard](https://dashboard.workos.com/webhooks) and add a Webhook endpoint with the public ngrok URL with `/webhooks` appended.

The local application is listening for webhook requests at http://localhost:7001/webhooks

### 4. Set Up Webhooks Secret

In order for the SDK to validate that WorkOS webhooks, locate the Webhook secret from the dashboard.

Then populate the following environment variable in your `.env` file at the root of the project.

```sh
WORKOS_WEBHOOK_SECRET=your_webhook_secret
```
Be sure to source the env variables so the application has access to them. 
```shell
source .env
```

### 5. Run the sample application

This will run the application on http://localhost:7001. Visit this URL from your browser and test sending webhooks from the WorkOS dashboard. The local application should display received webhooks live.

```
./gradlew :java-webhooks-example:run
```

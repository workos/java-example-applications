# WorkOS Java Example Applications

Run the following command to start all example applications in parallel.

```
./gradlew run
```

Or run individual applications like so.

```
./gradlew :java-sso-example:run
```

## Using Local SDK Version

If you would like to run the example applications with a version of the SDK published to your local Maven repository, simple pass the `-Pversion` flag.

```
./gradlew :java-sso-example:run -PsdkVersion=my-version
```

Note that Gradle will first attempt pulling the version from Maven Central, so the local version cannot be a previously published version.

plugins {
  id("com.workos.java.examples.java-common-conventions")

  application
}

dependencies {
  implementation("io.javalin:javalin:4.1.1")

  implementation("gg.jte:jte:1.12.0")

  if (project.hasProperty("sdkVersion")) {
    implementation("com.workos:workos:${project.property("sdkVersion")}")
  } else {
    implementation("com.workos:workos:1.0.0-beta-0-SNAPSHOT")
  }

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
}

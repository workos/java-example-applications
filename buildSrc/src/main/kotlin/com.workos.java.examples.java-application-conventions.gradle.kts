plugins {
  id("com.workos.java.examples.java-common-conventions")

  application
}

dependencies {
  implementation("io.javalin:javalin:4.1.1")

  implementation("gg.jte:jte:1.12.0")

  implementation("com.workos:workos:2.1.1")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")

  implementation("io.github.cdimascio:java-dotenv:5.2.2")
}

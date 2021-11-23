plugins {
  java
}

repositories {
  mavenCentral()

  mavenLocal()
}

dependencies {
  constraints {
    implementation("org.apache.commons:commons-text:1.9")
  }

  implementation("org.slf4j:slf4j-simple:1.7.32")

  testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
}

tasks.test {
  useJUnitPlatform()
}

java {
  toolchain {
    // Determines JDK version used for compilation. See https://docs.gradle.org/current/userguide/toolchains.html
    languageVersion.set(JavaLanguageVersion.of(8))
  }
}

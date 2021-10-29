plugins {
  java
}

repositories {
  mavenCentral()

  // Note: this will be removed once WorkOS SDK has been published to Maven central
  mavenLocal()
}

dependencies {
  constraints {
    implementation("org.apache.commons:commons-text:1.9")
  }

  testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
}

tasks.test {
  useJUnitPlatform()
}

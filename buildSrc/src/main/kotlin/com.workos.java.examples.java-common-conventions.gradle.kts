import gradle.kotlin.dsl.accessors._39401f6267fd4426380c4d61915a68af.implementation

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

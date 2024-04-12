plugins {
  id("com.workos.java.examples.java-application-conventions")
}

repositories {
  flatDir {
    dirs("libs")
    dirs("build/libs")
  }
}


application {
  mainClass.set("com.workos.java.examples.UserManagementApp")
}

package workos.java.example;

import io.javalin.Javalin;
import com.workos.WorkOS;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
      Javalin app = Javalin.create().start(8080);

      WorkOS workos = new WorkOS("apiKey");

      app.get("/", ctx -> ctx.result("Hello World"));
    }
}

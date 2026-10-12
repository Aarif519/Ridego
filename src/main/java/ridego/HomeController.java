package ridego;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                Welcome to RideGo Backend API!

                This is a Java Spring Boot ride-booking backend.

                To explore the APIs, visit:
                /swagger-ui/index.html
                """;
    }
}
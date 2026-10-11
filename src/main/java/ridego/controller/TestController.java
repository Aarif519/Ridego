package ridego.controller;
import java.util.List;

import ridego.entity.User;
import ridego.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final UserService userService;

public TestController(UserService userService) {
    this.userService = userService;
}
    @GetMapping("/api/admin/test")
public String adminTest() {
    return "Admin API Working";
}
@GetMapping("/api/admin/users")
public List<User> getAllUsersForAdmin() {

    return userService.getAllUsers();
}


    @GetMapping("/api/hello")
    public String hello() {
        return "RideGo Backend is Working!";
    }
}
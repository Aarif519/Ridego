package ridego.controller;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ridego.dto.LoginRequest;
import ridego.dto.LoginResponse;
import ridego.entity.User;
import ridego.service.UserService;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(
    summary = "User Login",
    description = "Authenticates a user and returns a JWT token"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Login successful"),
    @ApiResponse(responseCode = "401", description = "Invalid email or password")
})

    @PostMapping("/api/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(
                request.getEmail(),
                request.getPassword()
        );

        if (response == null) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "Invalid email or password");

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(error);
}
        return ResponseEntity.ok(response);
    }
    @Operation(
    summary = "Create User",
    description = "Creates a new user account"
)
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "User created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid user data or email already exists")
})

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(
            @Valid @RequestBody User user) {

        User savedUser = userService.saveUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }
    @Operation(
    summary = "Get All Users",
    description = "Returns a list of all registered users"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }
    @Operation(
    summary = "Get User By ID",
    description = "Returns a user by their unique ID"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "User found successfully"),
    @ApiResponse(responseCode = "404", description = "User not found"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }
@Operation(
    summary = "Delete User",
    description = "Deletes a user by their unique ID"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "User deleted successfully"),
    @ApiResponse(responseCode = "404", description = "User not found"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
@Operation(
    summary = "Update User",
    description = "Updates an existing user's information"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "User updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid user data"),
    @ApiResponse(responseCode = "404", description = "User not found"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {

        User updatedUser = userService.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }
}
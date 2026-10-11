package ridego.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ridego.dto.LoginResponse;
import ridego.entity.User;
import ridego.exception.UserNotFoundException;
import ridego.repository.UserRepository;
import ridego.security.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PersistenceContext
    private EntityManager entityManager;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Create User
    @Transactional
public User saveUser(User user) {
    user.setPassword(
        passwordEncoder.encode(user.getPassword())
    );

    user.setRole("USER");

    entityManager.persist(user);
    return user;
}

    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Get User By ID
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }

    // Delete User
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        userRepository.delete(user);
    }

    // Update User
    @Transactional
public User updateUser(Long id, User updatedUser) {

    User existingUser = userRepository.findById(id)
            .orElseThrow(() ->
                    new UserNotFoundException(
                            "User not found with id: " + id
                    )
            );

    existingUser.setName(updatedUser.getName());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setPhone(updatedUser.getPhone());

    // Role is intentionally not changed here.

    if (updatedUser.getPassword() != null
            && !updatedUser.getPassword().isBlank()) {

        existingUser.setPassword(
                passwordEncoder.encode(updatedUser.getPassword())
        );
    }

    return existingUser;
}

    // Login
    public LoginResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(
                password,
                user.getPassword())) {

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole()
            );

            return new LoginResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getRole(),
                    token
            );
        }

        return null;
    }
}
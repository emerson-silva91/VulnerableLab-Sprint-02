package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.LoginRequest;
import br.unipar.frameworks.dto.LoginResponse;
import br.unipar.frameworks.dto.RegisterRequest;
import br.unipar.frameworks.dto.UserResponse;
import br.unipar.frameworks.mapper.UserMapper;
import br.unipar.frameworks.model.User;
import br.unipar.frameworks.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(UserMapper.toResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userRepository.findByEmail(request.email())
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(
                        new LoginResponse(
                                "Login realizado para laboratório",
                                "TOKEN-" + user.getId() + "-" + user.getRole(),
                                UserMapper.toResponse(user)
                        )
                ))
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of(
                        "error", "Email ou senha inválidos"
                )));
    }
}
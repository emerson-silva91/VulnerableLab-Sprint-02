package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.UserResponse;
import br.unipar.frameworks.mapper.UserMapper;
import br.unipar.frameworks.model.User;
import br.unipar.frameworks.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Page<UserResponse> listUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserMapper.toResponse(user);
    }

    @GetMapping("/search")
    public Page<UserResponse> search(@RequestParam String term,
                                     @PageableDefault(size = 10) Pageable pageable) {
        return userRepository.safeSearchByName(term, pageable)
                .map(UserMapper::toResponse);
    }
}
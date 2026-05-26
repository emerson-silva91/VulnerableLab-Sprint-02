package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.UserResponse;
import br.unipar.frameworks.mapper.UserMapper;
import br.unipar.frameworks.model.User;
import br.unipar.frameworks.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserResponse> listUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserMapper.toResponse(user);
    }

    @GetMapping("/search-safe")
    public Page<UserResponse> safeSearch(@RequestParam String term,
                                         @PageableDefault(size = 10) Pageable pageable) {
        return userRepository.safeSearchByName(term, pageable)
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                ));
    }

    @GetMapping("/search-unsafe")
    public List<UserResponse> unsafeSearch(@RequestParam String term) {
        String jpql = "select u from User u where lower(u.name) like lower(:term)";
        return entityManager.createQuery(jpql, User.class)
                .setParameter("term", "%" + term + "%")
                .getResultList()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
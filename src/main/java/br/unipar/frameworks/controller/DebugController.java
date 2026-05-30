package br.unipar.frameworks.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
@Profile("lab")
@PreAuthorize("hasRole('ADMIN')")
public class DebugController {

    @GetMapping("/config")
    public Map<String, String> config() {
        return Map.of(
                "database", "H2 em memória",
                "h2Console", "/h2-console",
                "profile", "lab",
                "warning", "Endpoint disponível apenas em laboratório"
        );
    }

    @GetMapping("/error-example")
    public String errorExample() {
        throw new RuntimeException("Erro interno simulado");
    }
}
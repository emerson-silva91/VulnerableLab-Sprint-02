package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.ProductRequest;
import br.unipar.frameworks.dto.ProductResponse;
import br.unipar.frameworks.mapper.ProductMapper;
import br.unipar.frameworks.model.Product;
import br.unipar.frameworks.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Page<ProductResponse> listProducts(@PageableDefault(size = 10) Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @Valid @RequestBody ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow();

        ProductMapper.updateEntity(product, request);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }
}
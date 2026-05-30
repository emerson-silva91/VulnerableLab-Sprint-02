package br.unipar.frameworks.controller;

import br.unipar.frameworks.dto.CommentRequest;
import br.unipar.frameworks.dto.CommentResponse;
import br.unipar.frameworks.mapper.CommentMapper;
import br.unipar.frameworks.model.Comment;
import br.unipar.frameworks.model.Product;
import br.unipar.frameworks.repository.CommentRepository;
import br.unipar.frameworks.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public CommentController(CommentRepository commentRepository,
                             ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{productId}")
    public Page<CommentResponse> listByProduct(@PathVariable Long productId,
                                               @PageableDefault(size = 10) Pageable pageable) {
        return commentRepository.findByProductId(productId, pageable)
                .map(CommentMapper::toResponse);
    }

    @PostMapping
    public CommentResponse create(@Valid @RequestBody CommentRequest request) {
        Product product = productRepository.findById(request.productId()).orElseThrow();

        Comment comment = new Comment();
        comment.setText(request.text());
        comment.setProduct(product);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.toResponse(savedComment);
    }
}
package br.unipar.frameworks.mapper;

import br.unipar.frameworks.dto.CommentResponse;
import br.unipar.frameworks.model.Comment;

public class CommentMapper {

    public static CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getProduct().getId()
        );
    }
}
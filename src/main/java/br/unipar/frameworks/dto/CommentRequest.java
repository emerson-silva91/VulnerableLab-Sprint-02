package br.unipar.frameworks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequest(

        @NotBlank(message = "Comentário é obrigatório")
        @Size(max = 2000, message = "Comentário deve ter no máximo 2000 caracteres")
        String text,

        @NotNull(message = "Produto é obrigatório")
        Long productId
) {
}
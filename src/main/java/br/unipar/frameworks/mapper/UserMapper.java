package br.unipar.frameworks.mapper;

import br.unipar.frameworks.dto.UserResponse;
import br.unipar.frameworks.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

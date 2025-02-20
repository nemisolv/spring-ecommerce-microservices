package net.nemisolv.identity.mapper;

import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.payload.user.RoleResponse;
import net.nemisolv.identity.payload.user.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper {



    public RoleResponse toRoleResponse(User user) {
        return new RoleResponse(
                user.getRole().getId(),
                user.getRole().getName(),
                user.getRole().getPermissions().stream().map(permission -> permission.getName().name()).collect(Collectors.toSet())
        );
    }


    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .emailVerified(user.isEmailVerified())
                .id(user.getId())
                .username(user.getUsername())
                .role(toRoleResponse(user))

                .build();
    }

}

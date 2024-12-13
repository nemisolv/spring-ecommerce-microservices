package net.nemisolv.lib.mapper;

import net.nemisolv.techshop.entity.User;
import net.nemisolv.techshop.payload.user.FullUserInfo;
import net.nemisolv.techshop.payload.user.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper {

    public FullUserInfo toFullUserInfo(User user) {
        return new FullUserInfo(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isEmailVerified(),
                user.getImgUrl(),
                toRoleResponse(user),
                user.getAuthProvider().name()
        );
    }

    private RoleResponse toRoleResponse(User user) {
        return new RoleResponse(
                user.getRole().getId(),
                user.getRole().getName(),
                user.getRole().getPermissions().stream().map(permission -> permission.getName().name()).collect(Collectors.toSet())
        );
    }
}

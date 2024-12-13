package net.nemisolv.identity.mapper;

import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.payload.user.FullUserInfo;
import net.nemisolv.identity.payload.user.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper {

    public FullUserInfo toFullUserInfo(User user) {
        return new FullUserInfo(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
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

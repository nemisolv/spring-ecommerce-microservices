package net.nemisolv.identity.mapper;

import net.nemisolv.identity.entity.Permission;
import net.nemisolv.identity.payload.permission.PermissionRequest;
import net.nemisolv.identity.payload.permission.PermissionResponse;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public Permission toEntity(PermissionRequest request) {
        return Permission.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public PermissionResponse toResponse(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getName().name(),
                permission.getDescription()
        );
    }
}

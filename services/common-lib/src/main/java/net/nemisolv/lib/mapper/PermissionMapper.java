package net.nemisolv.lib.mapper;

import net.nemisolv.techshop.entity.Permission;
import net.nemisolv.techshop.payload.permission.PermissionRequest;
import net.nemisolv.techshop.payload.permission.PermissionResponse;
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

package net.nemisolv.identity.service.impl;

import lombok.RequiredArgsConstructor;

import net.nemisolv.identity.entity.Permission;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.helper.AccessHelper;
import net.nemisolv.identity.mapper.PermissionMapper;
import net.nemisolv.identity.payload.permission.AssignPermissionToRoleRequest;
import net.nemisolv.identity.payload.permission.PermissionResponse;
import net.nemisolv.identity.repository.PermissionRepository;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.security.context.AuthContext;
import net.nemisolv.identity.service.PermissionService;
import net.nemisolv.lib.core._enum.PermissionName;
import net.nemisolv.lib.core._enum.RoleName;
import net.nemisolv.lib.core.exception.BadRequestException;
import net.nemisolv.lib.core.exception.PermissionException;
import net.nemisolv.lib.core.exception.ResourceNotFoundException;
import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;
import net.nemisolv.lib.util.ResultCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static net.nemisolv.identity.helper.AccessHelper.isBasicPermission;


@Service
@RequiredArgsConstructor

public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public PagedResponse<PermissionResponse> getPermissions(QueryOption queryOption) {
        int page = queryOption.page();
        int limit = queryOption.limit();
        String sortBy = queryOption.sortBy();
        String sortDirection = queryOption.sortDirection();
        String search = queryOption.searchQuery();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Permission> permissionsPage = null;
        if(StringUtils.hasLength(search)) {
            permissionsPage = permissionRepository.searchPermissions(search, pageable);

        } else {
            permissionsPage = permissionRepository.findAll(pageable);
        }

        return mapToPagedResponse(permissionsPage);
    }

    private PagedResponse<PermissionResponse> mapToPagedResponse(Page<Permission> permissionsPage) {
        return PagedResponse.<PermissionResponse>builder()
                .records(
                        permissionsPage.getContent().stream()
                                .map(permissionMapper::toResponse)
                                .toList()
                )
                .pageNo(permissionsPage.getNumber())
                .pageSize(permissionsPage.getSize())
                .totalElements(permissionsPage.getTotalElements())
                .totalPages(permissionsPage.getTotalPages())
                .build();
    }

    @Override
    public PermissionResponse getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .map(permissionMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Permission not found with id: " + id));
    }

    @Override
    public List<PermissionResponse> getPermissionsByRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isEmpty()) {
            throw new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Role not found with id: " + roleId);
        }
        return role.get().getPermissions().stream()
                .map(permissionMapper::toResponse)
                .toList();


    }



    @Override
    public PermissionResponse assignPermissionToRole(AssignPermissionToRoleRequest request) {
        Long roleId = request.roleId();
        Long permissionId = request.permissionId();

        UserPrincipal currentUser = AuthContext.getCurrentUser();

        if (currentUser == null || !AccessHelper.isAccessAllowed(PermissionName.ASSIGN_ROLE)) {
            throw new AccessDeniedException("Permission denied to assign role.");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Role not found with id: " + roleId));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Permission not found with id: " + permissionId));

        if(role.getPermissions().contains(permission)) {
            throw new BadRequestException(ResultCode.PERMISSION_ALREADY_ASSIGNED);
        }

        if (currentUser.getRole().getName() == RoleName.ADMIN) {
            // Admin can assign any permission
            role.getPermissions().add(permission);
        } else if (currentUser.getRole().getName() == RoleName.MANAGER) {
            // Manager can only assign basic permissions
            if (isBasicPermission(permission)) {
                role.getPermissions().add(permission);
            } else {
                throw new PermissionException("Managers cannot assign advanced permissions.");
            }
        }

        roleRepository.save(role);
        return permissionMapper.toResponse(permission);
    }

    @Override
    public List<PermissionResponse> updatePermissionsForRole(Long roleId, List<Long> permissionIds) {
        UserPrincipal currentUser = AuthContext.getCurrentUser();

        if (currentUser == null || !AccessHelper.isAccessAllowed(PermissionName.ASSIGN_ROLE)) {
            throw new PermissionException(ResultCode.USER_PERMISSION_ERROR,"Permission denied to assign role permissions.");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Role not found with id: " + roleId));
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);



        if(permissionIds.size() != permissions.size()) {
            throw new ResourceNotFoundException(ResultCode.RESOURCE_NOT_FOUND,"Some permissions not found");
        }



        if (currentUser.getRole().getName() == RoleName.ADMIN) {
            // check if role also is admin, if so, throw exception
            if(role.getName() == RoleName.ADMIN) {
                throw new PermissionException(ResultCode.USER_PERMISSION_ERROR,"You are admin with all permissions, you cannot update permissions for admin role.");
            }


            // Admin can update all permissions for the role
            role.setPermissions(new HashSet<>(permissions));
        } else if (currentUser.getRole().getName() == RoleName.MANAGER) {
            // a manager can't update permissions for admin, manager, You can only update for roles below your role
            if(role.getName() == RoleName.ADMIN || role.getName() == RoleName.MANAGER) {
                throw new PermissionException(
                        ResultCode.USER_PERMISSION_ERROR,"You are manager with limited permissions," +
                        " you cannot update permissions for admin or manager role.");
            }

            // Manager can only update basic permissions for the role
            if (permissions.stream().anyMatch(permission -> !isBasicPermission(permission))) {
                throw new PermissionException("Managers cannot assign advanced permissions.");
            }
            role.setPermissions(new HashSet<>(permissions));
        }
        Role savedRole = roleRepository.save(role);
        return savedRole.getPermissions().stream()
                .map(permissionMapper::toResponse)
                .toList();

    }
}

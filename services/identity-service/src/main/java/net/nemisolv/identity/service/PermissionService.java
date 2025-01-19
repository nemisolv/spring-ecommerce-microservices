package net.nemisolv.identity.service;


import net.nemisolv.identity.payload.permission.AssignPermissionToRoleRequest;
import net.nemisolv.identity.payload.permission.AuthorityResponse;
import net.nemisolv.identity.payload.permission.PermissionResponse;
import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;

import java.util.List;

public interface PermissionService {
//    PermissionResponse createPermission(PermissionRequest request);
//    PermissionResponse updatePermission(Long id, PermissionRequest request);
    void deletePermission(Long id);
    PagedResponse<PermissionResponse> getPermissions(QueryOption queryOption);
    PermissionResponse getPermissionById(Long id);
    List<PermissionResponse> getPermissionsByRole(Long roleId);

    PermissionResponse assignPermissionToRole(AssignPermissionToRoleRequest request);

    List<PermissionResponse> updatePermissionsForRole(Long roleId, List<Long> permissionIds);

    AuthorityResponse getMyAuthorities();
}

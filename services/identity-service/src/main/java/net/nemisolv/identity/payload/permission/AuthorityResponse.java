package net.nemisolv.identity.payload.permission;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class AuthorityResponse {
    private String roleId;
    private String roleName;
    private List<String> permissions;
}

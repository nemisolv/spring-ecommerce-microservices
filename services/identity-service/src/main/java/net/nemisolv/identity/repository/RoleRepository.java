package net.nemisolv.identity.repository;


import net.nemisolv.identity.entity.Role;
import net.nemisolv.lib.core._enum.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName role);
}

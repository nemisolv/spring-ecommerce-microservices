package net.nemisolv.identity.repository;

import net.nemisolv.identity.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = "SELECT * FROM permission p WHERE p.name LIKE %:search% OR p.description LIKE %:search%",
            countQuery = "SELECT count(*) FROM permission p WHERE p.name LIKE %:search% OR p.description LIKE %:search%",
            nativeQuery = true)
    Page<Permission> searchPermissions(String search, Pageable pageable);
}

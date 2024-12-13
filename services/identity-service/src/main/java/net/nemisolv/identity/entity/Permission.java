package net.nemisolv.identity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.nemisolv.lib.core._enum.PermissionName;

import java.util.Set;

@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class Permission extends IdBaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PermissionName name;

    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    public Permission(PermissionName name, String description) {
        this.name = name;
        this.description = description;
    }

}
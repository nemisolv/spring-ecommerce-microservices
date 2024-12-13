package net.nemisolv.identity.payload.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.nemisolv.lib.core._enum.RoleName;
import net.nemisolv.lib.core.validation.EnumValue;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public class RegisterInternalRequest extends RegisterRequest {
    @NotNull(message = "Role can not be null")
    @EnumValue(enumClass = RoleName.class, message = "Role is not valid")
    private RoleName role;
}
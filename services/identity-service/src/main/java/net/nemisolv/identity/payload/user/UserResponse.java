package net.nemisolv.identity.payload.user;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse

{
    Long id;
    String username;
    String email;
    boolean emailVerified;
    RoleResponse role;
    String profileId;
}

package net.nemisolv.identity.payload.profile;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProfileRequest
{

    @NotNull(message = "userId is required")
    String userId;
    String username;
    String email;
    String name;
}

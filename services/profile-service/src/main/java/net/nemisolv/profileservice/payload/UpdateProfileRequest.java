package net.nemisolv.profileservice.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileRequest {
    @NotNull(message = "userId is required")
    String userId;
    String username;
    String email;
    String name;
    String imgUrl;
    String authProvider;
}

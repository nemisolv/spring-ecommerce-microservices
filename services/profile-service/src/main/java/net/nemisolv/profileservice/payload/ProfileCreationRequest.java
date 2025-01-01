package net.nemisolv.profileservice.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    @NotNull(message = "User ID is required")
    String userId;
    @NotNull(message = "Username is required")
    String username;
    String email;
    @NotEmpty(message = "Name is required")
    String name;
    String phoneNumber;
    String imgUrl;
    String address;
}
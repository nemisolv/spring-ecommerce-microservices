package net.nemisolv.profileservice.payload;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String username;
    String email;
    String name;
    String phoneNumber;
    String imgUrl;
    String address;
    String userId;
}
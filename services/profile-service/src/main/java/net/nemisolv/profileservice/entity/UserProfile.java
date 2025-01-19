package net.nemisolv.profileservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Document
public class UserProfile {
    @Id
    private String id;
    private String name;
    private String imgUrl;
    private String address;
    private String phoneNumber;
    private String userId;
    private String email;
    private String username;
    private String authProvider;
}

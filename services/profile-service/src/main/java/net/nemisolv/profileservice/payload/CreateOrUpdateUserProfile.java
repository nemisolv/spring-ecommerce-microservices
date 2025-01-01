package net.nemisolv.profileservice.payload;

public record CreateOrUpdateUserProfile(
        String username,
        String name,
        String phoneNumber,
        String imgUrl,
        String address,
        String userId,
        String email
) {
}

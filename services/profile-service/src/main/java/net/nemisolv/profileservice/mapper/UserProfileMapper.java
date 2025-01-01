package net.nemisolv.profileservice.mapper;

import net.nemisolv.profileservice.entity.UserProfile;
import net.nemisolv.profileservice.payload.CreateProfileRequest;
import net.nemisolv.profileservice.payload.UserProfileResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(CreateProfileRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
package net.nemisolv.identity.mapper;

import net.nemisolv.identity.payload.auth.CreateUserRequest;
import net.nemisolv.identity.payload.profile.CreateProfileRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    CreateProfileRequest toProfileCreationRequest(CreateUserRequest request);
}

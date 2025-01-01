package net.nemisolv.profileservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.profileservice.payload.ProfileCreationRequest;
import net.nemisolv.profileservice.payload.UserProfileResponse;
import net.nemisolv.profileservice.service.UserProfileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/internal/users")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody @Valid  ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.createProfile(request))
                .build();
    }
}
package net.nemisolv.profileservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.profileservice.payload.CreateProfileRequest;
import net.nemisolv.profileservice.payload.UpdateProfileRequest;
import net.nemisolv.profileservice.payload.UserProfileResponse;
import net.nemisolv.profileservice.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    // internal that means other services can access this endpoint

    @PostMapping("/internal/users")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody @Valid CreateProfileRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.createProfile(request))
                .build();
    }

    @PutMapping("/internal/users")
    ApiResponse<UserProfileResponse> updateProfile(@RequestBody @Valid UpdateProfileRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.updateProfile(request))
                .build();
    }
}
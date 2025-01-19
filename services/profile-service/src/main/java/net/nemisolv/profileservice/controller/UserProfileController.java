package net.nemisolv.profileservice.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;
import net.nemisolv.profileservice.payload.CreateOrUpdateUserProfile;
import net.nemisolv.profileservice.payload.UserProfileResponse;
import net.nemisolv.profileservice.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/{profileId}")

    ApiResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.getProfile(profileId))
                .build();
    }

    @GetMapping
    ApiResponse<PagedResponse<UserProfileResponse>> getAllProfiles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "") String searchQuery ) {
        QueryOption queryOption = new QueryOption(page, limit, sortBy, sortDirection, searchQuery, "", true);
        return ApiResponse.<PagedResponse<UserProfileResponse>>builder()
                .data(userProfileService.getAllProfiles(queryOption))
                .build();
    }

    @GetMapping("/my-profile")
    ApiResponse<UserProfileResponse> getMyProfile() {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.getMyProfile())
                .build();
    }

    @PostMapping("/create-or-update-profile")
    ApiResponse<UserProfileResponse> createOrUpdateProfile(@RequestBody CreateOrUpdateUserProfile userProfile) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.createOrUpdateProfile(userProfile))
                .build();
    }
}
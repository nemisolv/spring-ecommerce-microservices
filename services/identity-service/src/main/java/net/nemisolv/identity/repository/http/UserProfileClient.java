package net.nemisolv.identity.repository.http;

import jakarta.validation.Valid;
import net.nemisolv.identity.payload.profile.*;
import net.nemisolv.lib.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "profile-service", url = "${app.profile-service.url}")
public interface UserProfileClient {
    @PostMapping("/users/create-or-update-profile")
    UserProfileResponse createOrUpdateUserProfile(@Valid  CreateOrUpdateUserProfile userProfileRequest);

    @GetMapping("/users/{userId}")
    UserProfileResponse getUserProfile(@PathVariable("userId") String userId);


    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody @Valid CreateProfileRequest request);

    // openfeign does not support @PatchMapping directly, if you want to use @PatchMapping, you must configure OkHttpClient
    @PutMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> updateProfile(@RequestBody @Valid UpdateProfileRequest request);


}



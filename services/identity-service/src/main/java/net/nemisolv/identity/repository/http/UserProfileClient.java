package net.nemisolv.identity.repository.http;

import jakarta.validation.Valid;
import net.nemisolv.identity.payload.profile.CreateOrUpdateUserProfile;
import net.nemisolv.identity.payload.profile.CreateProfileRequest;
import net.nemisolv.identity.payload.profile.GetProfileRequest;
import net.nemisolv.identity.payload.profile.UserProfileResponse;
import net.nemisolv.lib.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.profile-service.url}")
public interface UserProfileClient {
    @PostMapping("/users/create-or-update-profile")
    UserProfileResponse createOrUpdateUserProfile(@Valid  CreateOrUpdateUserProfile userProfileRequest);

    @GetMapping("/users/{userId}")
    UserProfileResponse getUserProfile(@PathVariable("userId") String userId);


    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody @Valid CreateProfileRequest request);


}



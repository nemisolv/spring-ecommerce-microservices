package net.nemisolv.identity.repository.http;

import jakarta.validation.Valid;
import net.nemisolv.identity.payload.profile.CreateOrUpdateUserProfile;
import net.nemisolv.identity.payload.profile.GetProfileRequest;
import net.nemisolv.identity.payload.profile.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "profile-service", url = "${app.profile-service.url}")
public interface UserProfileClient {
    @PostMapping("/users/create-or-update-profile")
    UserProfileResponse createOrUpdateUserProfile(@Valid  CreateOrUpdateUserProfile userProfileRequest);

    @GetMapping("/users/{userId}")
    UserProfileResponse getUserProfile(@PathVariable("userId") String userId);}

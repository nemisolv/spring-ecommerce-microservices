package net.nemisolv.profileservice.service;

import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;
import net.nemisolv.profileservice.payload.CreateOrUpdateUserProfile;
import net.nemisolv.profileservice.payload.CreateProfileRequest;
import net.nemisolv.profileservice.payload.UserProfileResponse;



public interface UserProfileService {

    UserProfileResponse createProfile(CreateProfileRequest request) ;

    UserProfileResponse getProfile(String id) ;

    PagedResponse<UserProfileResponse> getAllProfiles(QueryOption queryOption) ;

    UserProfileResponse getMyProfile() ;


    UserProfileResponse createOrUpdateProfile(CreateOrUpdateUserProfile userProfile);
}
package net.nemisolv.profileservice.service.impl;

import cn.hutool.setting.profile.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.lib.core.exception.BadRequestException;
import net.nemisolv.lib.core.exception.BusinessException;
import net.nemisolv.lib.payload.PagedResponse;
import net.nemisolv.lib.payload.QueryOption;
import net.nemisolv.lib.util.ResultCode;
import net.nemisolv.profileservice.entity.UserProfile;
import net.nemisolv.profileservice.mapper.UserProfileMapper;
import net.nemisolv.profileservice.payload.CreateOrUpdateUserProfile;
import net.nemisolv.profileservice.payload.CreateProfileRequest;
import net.nemisolv.profileservice.payload.UpdateProfileRequest;
import net.nemisolv.profileservice.payload.UserProfileResponse;
import net.nemisolv.profileservice.repository.UserProfileRepository;
import net.nemisolv.profileservice.service.UserProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse createProfile(CreateProfileRequest request) {
        UserProfile profile = UserProfile.builder()
                        .name(request.getName())
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .imgUrl(request.getImgUrl())
                        .build();

        if(StringUtils.hasLength(request.getAuthProvider())) {
            profile.setAuthProvider(request.getAuthProvider());
        }else {
            profile.setAuthProvider("LOCAL");
        }
        userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileResponse(profile);
    }


    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public PagedResponse<UserProfileResponse> getAllProfiles(QueryOption queryOption) {
        int pageNo = queryOption.page() - 1; // minus 1 because machine starts counting from 0 but humans start counting from 1
        int pageSize = queryOption.limit();
        String sortBy = queryOption.sortBy();
        String sortDirection = queryOption.sortDirection();
        String searchQuery = queryOption.searchQuery();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<UserProfile> profiles;

        if (StringUtils.hasLength(searchQuery)) {
            profiles = userProfileRepository.searchProfile(searchQuery, pageable);
        } else {
            profiles = userProfileRepository.findAll(pageable);
        }

        return PagedResponse.<UserProfileResponse>builder()
                .records(profiles.getContent().stream().map(userProfileMapper::toUserProfileResponse).toList())
                .pageNo(profiles.getNumber() + 1)
                .pageSize(profiles.getSize())
                .totalElements(profiles.getTotalElements())
                .totalPages(profiles.getTotalPages())
                .build();

    }

    public UserProfileResponse getMyProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND_OR_DISABLED);
        }


        String userId = authentication.getName();

        var profile = userProfileRepository.findByEmail(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND_OR_DISABLED));

        return userProfileMapper.toUserProfileResponse(profile);
    }

    @Override
    public UserProfileResponse createOrUpdateProfile(CreateOrUpdateUserProfile userProfile) {
        String userId = userProfile.userId();
        if(!StringUtils.hasLength(userId)) {
            throw new BadRequestException(ResultCode.BAD_REQUEST,"User id is required");
        }
        UserProfile profile = userProfileRepository.findByUserId(userId).orElseGet(UserProfile::new);
        profile.setName(userProfile.name());
        profile.setPhoneNumber(userProfile.phoneNumber());
        if(!StringUtils.hasLength(userProfile.address())) {
            profile.setAddress(userProfile.address());
        }
        if(!StringUtils.hasLength(userProfile.imgUrl())) {
            profile.setEmail(userProfile.imgUrl());
        }

        if(!StringUtils.hasLength(userProfile.email())) {
            profile.setEmail(userProfile.email());
        }

        profile = userProfileRepository.save(profile);

        return userProfileMapper.toUserProfileResponse(profile);
    }

    @Override
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        String userId = request.getUserId();
        if(!StringUtils.hasLength(userId)) {
            throw new BadRequestException(ResultCode.BAD_REQUEST,"User id is required");
        }

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND_OR_DISABLED));
        profile.setName(request.getName());
        profile.setUsername(request.getUsername());
        if(StringUtils.hasLength(request.getImgUrl())) {
            profile.setImgUrl(request.getImgUrl());
        }

        if(StringUtils.hasLength(request.getEmail())) {
            profile.setEmail(request.getEmail());
        }

        if(StringUtils.hasLength(request.getAuthProvider())) {
            profile.setAuthProvider(request.getAuthProvider());
        }else {
            profile.setAuthProvider("LOCAL");
        }


        userProfileRepository.save(profile);
        return userProfileMapper.toUserProfileResponse(profile);
    }
}

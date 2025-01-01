package net.nemisolv.identity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.entity.ConfirmationEmail;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.exception.BadRequestException;
import net.nemisolv.identity.exception.ResourceNotFoundException;
import net.nemisolv.identity.helper.UserHelper;
import net.nemisolv.identity.kafka.AuthProducer;
import net.nemisolv.identity.mapper.ProfileMapper;
import net.nemisolv.identity.mapper.UserMapper;
import net.nemisolv.identity.payload.auth.*;
import net.nemisolv.identity.payload.user.UserResponse;
import net.nemisolv.identity.repository.ConfirmationEmailRepository;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.identity.repository.http.UserProfileClient;
import net.nemisolv.identity.service.SendMailService;
import net.nemisolv.identity.service.UserService;
import net.nemisolv.lib.core._enum.AuthProvider;
import net.nemisolv.lib.core._enum.MailType;
import net.nemisolv.lib.core._enum.NotificationType;
import net.nemisolv.lib.core._enum.RoleName;
import net.nemisolv.lib.event.dto.NotificationEvent;
import net.nemisolv.lib.util.CommonUtil;
import net.nemisolv.lib.util.Constants;
import net.nemisolv.lib.util.CryptoUtil;
import net.nemisolv.lib.util.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static net.nemisolv.lib.util.Constants.getExpTimeRegistrationEmail;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserProfileClient userProfileClient;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    private final SendMailService sendMailService;




    @Override
    public UserResponse createUser(CreateUserRequest request) {
        String email = request.email();

        Optional<User> userByEmailOptional = userRepository.findByEmail(email);

        String hashedPassword = passwordEncoder.encode(request.password());
        Role roleToAssign = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException(ResultCode.ROLE_NOT_FOUND, "Role not found"));

        User user;
        if (userByEmailOptional.isPresent()) {
            User existingUser = userByEmailOptional.get();
            if (existingUser.isEmailVerified()) {
                throw new BadRequestException(ResultCode.USER_ALREADY_EXISTS, "Email already exists");
            }
            existingUser.setPassword(hashedPassword);
            existingUser.setRole(roleToAssign);
            existingUser.setUsername(userHelper.generateUsername(request.name()));
            user = saveUser(existingUser);
        } else {
            user = User.builder()
                    .email(email)
                    .username(userHelper.generateUsername(request.name()))
                    .password(hashedPassword)
                    .role(roleToAssign)
                    .emailVerified(false)
                    .enabled(true)
                    .authProvider(AuthProvider.LOCAL)
                    .providerId(null)
                    .build();
            user = saveUser(user);
        }


        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(String.valueOf(user.getId()));
        profileRequest.setUsername(user.getUsername());
        var profileResponse = userProfileClient.createProfile(profileRequest);

        sendMailService.sendEmailConfirmation(email, request.name());

        var userResponse = userMapper.toUserResponse(user);
        userResponse.setProfileId(profileResponse.getData().getId());
        return userResponse;
    }

    private User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException(ResultCode.DIRTY_DATA, "Email already exists");
        }
    }


}

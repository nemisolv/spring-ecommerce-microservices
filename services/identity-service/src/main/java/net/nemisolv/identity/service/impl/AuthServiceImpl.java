package net.nemisolv.identity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.entity.ConfirmationEmail;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.helper.UserHelper;
import net.nemisolv.identity.mapper.UserMapper;
import net.nemisolv.identity.payload.auth.*;
import net.nemisolv.identity.repository.ConfirmationEmailRepository;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.security.context.AuthContext;
import net.nemisolv.identity.service.AuthService;
import net.nemisolv.identity.service.JwtService;
import net.nemisolv.lib.core._enum.AuthProvider;
import net.nemisolv.lib.core._enum.MailType;
import net.nemisolv.lib.core._enum.RoleName;
import net.nemisolv.lib.core.exception.BadCredentialException;
import net.nemisolv.lib.core.exception.BadRequestException;
import net.nemisolv.lib.core.exception.PermissionException;
import net.nemisolv.lib.core.exception.ResourceNotFoundException;
import net.nemisolv.lib.util.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ConfirmationEmailRepository confirmationEmailRepo;
    private final UserHelper userHelper;
    private final UserMapper userMapper;
//    private final EmailService emailService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if (!userPrincipal.isEmailVerified()) {
                throw new BadCredentialException(ResultCode.USER_EMAIL_NOT_VERIFIED, "Email not verified");
            }

            return generateAuthResponse(userPrincipal);

        } catch (AuthenticationException ex) {
            throw new BadCredentialException(ResultCode.USER_AUTH_ERROR);
        }
    }

    @Override
    @Transactional
    public void registerExternal(RegisterRequest authRequest) throws MessagingException {
        Optional<User> existingUser = userRepo.findByEmail(authRequest.getEmail());

        if (existingUser.isPresent()) {
            handleExistingUserForRegistration(existingUser.get(), authRequest);
        } else {
            createAndSendVerificationEmail(authRequest);
        }
    }

    @Override
    public void registerInternal(RegisterInternalRequest authRequest) {
        if (userRepo.existsByEmail(authRequest.getEmail())) {
            throw new BadCredentialException(ResultCode.USER_ALREADY_EXISTS);
        }
        UserPrincipal currentUser  = AuthContext.getCurrentUser();
//        if(userPrincipal.getAuthorities() )

            Role roleToAssign = roleRepo.findByName(authRequest.getRole())
                    .orElseThrow(() -> new BadRequestException(ResultCode.ROLE_NOT_FOUND));

        // Kiểm tra quyền của user hiện tại
        if (currentUser.getRole().getName().equals(RoleName.ADMIN)) {
            // Admin có thể gán bất kỳ role nào
        } else if (currentUser.getRole().getName().equals(RoleName.MANAGER)) {
            // Manager chỉ được gán các role cụ thể
            if (!isAssignableByManager(roleToAssign)) {
                throw new PermissionException("Managers cannot assign this role.");
            }
        } else {
            throw new PermissionException("Unauthorized to assign roles.");
        }

        User newUser = createUser(authRequest, roleToAssign);
        userRepo.save(newUser);
    }

    @Override
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String refreshToken = extractBearerToken(req.getHeader(HttpHeaders.AUTHORIZATION));
        if (refreshToken == null) return;

        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (jwtService.isValidToken(refreshToken, UserPrincipal.create(user))) {
                writeAuthResponse(res, generateAuthResponse(UserPrincipal.create(user)));
            }
        }
    }

    @Override
    public void verifyEmail(String token) {

    }

//    @Override
//    public void verifyEmail(String token) {
//        ConfirmationEmail confirmationEmail = validateConfirmationEmail(token);
//
//        User user = userRepo.findById(confirmationEmail.getUserId())
//                .orElseThrow(() -> new BadRequestException(ResultCode.USER_NOT_FOUND));
//
//        user.setEmailVerified(true);
//        confirmationEmail.setConfirmedAt(LocalDateTime.now());
//
//        userRepo.save(user);
//        confirmationEmailRepo.save(confirmationEmail);
//    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        // TODO: Implement email sending logic
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetRequest) {
//        ConfirmationEmail confirmationEmail = validateConfirmationEmail(resetRequest.getToken());
//
//        User user = userRepo.findById(confirmationEmail.getUserId())
//                .orElseThrow(() -> new BadRequestException(ResultCode.USER_NOT_FOUND));
//
//        user.setPassword(passwordEncoder.encode(resetRequest.getNewPassword()));
//        confirmationEmail.setConfirmedAt(LocalDateTime.now());
//
//        userRepo.save(user);
//        confirmationEmailRepo.save(confirmationEmail);

    }

    @Override
    public IntrospectResponse introspectToken(IntrospectRequest request) {
        return jwtService.introspectToken(request);
    }

    /**
     * Helper Methods
     */

    private AuthenticationResponse generateAuthResponse(UserPrincipal userPrincipal) {
        User user = userRepo.findByEmail(userPrincipal.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return AuthenticationResponse.builder()
                .userData(userMapper.toFullUserInfo(user))
                .accessToken(jwtService.generateToken(userPrincipal))
                .refreshToken(jwtService.generateRefreshToken(userPrincipal))
                .build();
    }

    private void handleExistingUserForRegistration(User user, RegisterRequest authRequest) throws MessagingException {
        if (user.isEmailVerified()) {
            throw new BadCredentialException(ResultCode.USER_ALREADY_EXISTS);
        }

        updateExistingUser(user, authRequest);
        sendVerificationEmail(user);
    }

    private void createAndSendVerificationEmail(RegisterRequest authRequest) throws MessagingException {
        Role customerRole = roleRepo.findByName(RoleName.CUSTOMER).orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User newUser = createUser(authRequest, customerRole);
        userRepo.save(newUser);

        sendVerificationEmail(newUser);
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        String token = jwtService.generateToken(UserPrincipal.create(user));

        ConfirmationEmail confirmationEmail = ConfirmationEmail.builder()
                .token(token)
                .type(MailType.REGISTRATION_CONFIRMATION)
                .expiredAt(LocalDateTime.now().plusDays(1))
                .userId(user.getId())
                .build();

        confirmationEmailRepo.save(confirmationEmail);
//        emailService.sendRegistrationEmail(user, token);
        // send email -> using email service
    }

    private User createUser(RegisterRequest authRequest, Role role) {



        return User.builder()
                .email(authRequest.getEmail())
                .username(userHelper.generateUsername(authRequest.getName(), ""))
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .name(authRequest.getName())
                .emailVerified(false)
                .enabled(true)
                .authProvider(AuthProvider.LOCAL)
                .role(role)
                .build();
    }

    private void updateExistingUser(User user, RegisterRequest authRequest) {
        user.setName(authRequest.getName());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        confirmationEmailRepo.findByTypeAndUserId(MailType.REGISTRATION_CONFIRMATION, user.getId())
                .forEach(confirmationEmail -> {
                    confirmationEmail.setRevoked(true);
                    confirmationEmailRepo.save(confirmationEmail);
                });

        userRepo.save(user);
    }

    private ConfirmationEmail validateConfirmationEmail(String token) {
        ConfirmationEmail confirmationEmail = confirmationEmailRepo.findByToken(token)
                .orElseThrow(() -> new BadRequestException(ResultCode.AUTH_TOKEN_INVALID));

        if (confirmationEmail.isRevoked() || confirmationEmail.getExpiredAt().isBefore(LocalDateTime.now())
                || confirmationEmail.getConfirmedAt() != null) {
            throw new BadRequestException(ResultCode.AUTH_TOKEN_INVALID);
        }

        return confirmationEmail;
    }

    private String extractBearerToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    private void writeAuthResponse(HttpServletResponse res, AuthenticationResponse authResponse) throws IOException {
        new ObjectMapper().writeValue(res.getOutputStream(), authResponse);
    }

    private boolean isAssignableByManager(Role role) {
        List<String> allowedRolesForManager = List.of("STAFF", "ASSISTANT");
        return allowedRolesForManager.contains(role.getName().toString());
    }

}

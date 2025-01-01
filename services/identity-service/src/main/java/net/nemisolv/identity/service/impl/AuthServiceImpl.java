package net.nemisolv.identity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.entity.ConfirmationEmail;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.exception.BadCredentialException;
import net.nemisolv.identity.exception.BadRequestException;
import net.nemisolv.identity.helper.UserHelper;
import net.nemisolv.identity.kafka.AuthProducer;
import net.nemisolv.identity.mapper.UserMapper;
import net.nemisolv.identity.payload.auth.*;
import net.nemisolv.identity.payload.profile.UserProfileResponse;
import net.nemisolv.identity.repository.ConfirmationEmailRepository;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.identity.repository.http.UserProfileClient;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.security.context.AuthContext;
import net.nemisolv.identity.service.AuthService;
import net.nemisolv.identity.service.JwtService;
import net.nemisolv.identity.service.SendMailService;
import net.nemisolv.lib.core._enum.MailType;
import net.nemisolv.lib.util.ResultCode;
import net.nemisolv.lib.util.TokenUtil;
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
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import static net.nemisolv.lib.util.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ConfirmationEmailRepository confirmationEmailRepo;
    private final UserProfileClient userProfileClient;
    private final SendMailService sendMailService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if (!userPrincipal.isEmailVerified()) {
                throw new BadCredentialException(ResultCode.EMAIL_NOT_VERIFIED, "Email not verified");
            }

            return generateAuthResponse(userPrincipal);

        } catch (AuthenticationException ex) {
            throw new BadCredentialException(ResultCode.AUTHENTICATION_FAILED);
        }
    }



    @Override
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String refreshToken = extractBearerToken(req.getHeader(HttpHeaders.AUTHORIZATION));
        if (refreshToken == null) return;

        String userEmail = jwtService.extractSubject(refreshToken);
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
        ConfirmationEmail confirmationEmail = validateConfirmationEmail(token);

        User user = userRepo.findByEmail(confirmationEmail.getIdentifier())
                .orElseThrow(() -> new BadRequestException(ResultCode.USER_NOT_FOUND_OR_DISABLED));

        user.setEmailVerified(true);
        confirmationEmail.setConfirmedAt(LocalDateTime.now());

        userRepo.save(user);
        confirmationEmailRepo.save(confirmationEmail);
    }

//

    @Override
    public void forgotPassword(String email) throws NoSuchAlgorithmException {
// don't use sha256hash because it is the same output in any cases
//        String token = CryptoUtil.sha256Hash(email + authSecret);
        String token = TokenUtil.generateRandomToken();
        User user = userRepo.findByEmail(email)
                // don't leak information about whether the email exists or not, just throw common exception
                .orElseThrow(() -> new BadRequestException(ResultCode.BAD_REQUEST));

        ConfirmationEmail resetPasswordConfirmation = ConfirmationEmail.builder()
                .identifier(email)
                .token(token)
                .type(MailType.PASSWORD_RESET)
                .expiredAt(getExpTimePasswordResetEmail())
                .build();


        // revoke all previous reset password tokens
        confirmationEmailRepo.findByTypeAndIdentifier(MailType.PASSWORD_RESET, user.getEmail())
                .forEach(confirmationEmail -> {
                    confirmationEmail.setRevoked(true);
                    confirmationEmailRepo.save(confirmationEmail);
                });



        confirmationEmailRepo.save(resetPasswordConfirmation);


        // TODO: Implement email sending logic

        UserProfileResponse userProfileResponse = userProfileClient.getUserProfile(user.getId().toString());


//        authProducer.sendForgotPasswordEmail(new SendMailWithOtpToken(
//                RecipientInfo.builder()
//                        .email(user.getEmail())
//                        .name(userProfileResponse.getName())
//                        .build()
//                ,
//                OtpTokenOptional.builder()
//                        .otp(null)
//                        .token(token)
//                        .build()
//
//        ));
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetRequest) {
        String token = resetRequest.getToken();
        String newPassword = resetRequest.getPassword();

        ConfirmationEmail confirmationEmail = validateConfirmationEmail(token);
        confirmationEmail.setConfirmedAt(LocalDateTime.now());

        User user = userRepo.findByEmail(confirmationEmail.getIdentifier())
                .orElseThrow(() -> new BadRequestException(ResultCode.USER_NOT_FOUND_OR_DISABLED));

        user.setPassword(passwordEncoder.encode(newPassword));
        confirmationEmailRepo.save(confirmationEmail);
        userRepo.save(user);

    }

    @Override
    public IntrospectResponse introspectToken(IntrospectRequest request) {
        return jwtService.introspectToken(request);
    }

    @Override
    public void resendEmailConfirmation(ResendEmailConfirmationRequest request) {
        String normalizedEmail = request.email().toLowerCase();
        User user = userRepo.findByEmail(normalizedEmail)
                .orElseThrow(() -> new BadRequestException(ResultCode.USER_NOT_FOUND_OR_DISABLED));

        if(user.isEmailVerified()) {
            throw new BadRequestException(ResultCode.EMAIL_ALREADY_VERIFIED);
        }



        confirmationEmailRepo.findByTypeAndIdentifier(MailType.REGISTRATION_CONFIRMATION, user.getEmail())
                .forEach(confirmationEmail -> {
                    confirmationEmail.setRevoked(true);
                    confirmationEmailRepo.save(confirmationEmail);
                });

        try {
            String name = null; // call to profile service to get the name
            sendMailService.sendEmailConfirmation(user.getEmail(),name);
        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse getAuthResponse() {
        UserPrincipal userPrincipal = AuthContext.getCurrentUser();
        return generateAuthResponse(userPrincipal);
    }

    /**
     * Helper Methods
     */

    private AuthenticationResponse generateAuthResponse(UserPrincipal userPrincipal) {

        String token = jwtService.generateToken(userPrincipal);
        String refreshToken = jwtService.generateRefreshToken(userPrincipal);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }




    private ConfirmationEmail validateConfirmationEmail(String token) {
        ConfirmationEmail confirmationEmail = confirmationEmailRepo.findByToken(token)
                .orElseThrow(() -> new BadRequestException(ResultCode.INVALID_TOKEN));

        if(confirmationEmail.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException(ResultCode.TOKEN_EXPIRED);
        }
        if(confirmationEmail.getConfirmedAt() != null) {
            if(confirmationEmail.getType() == MailType.REGISTRATION_CONFIRMATION) {
            throw new BadRequestException(ResultCode.EMAIL_ALREADY_VERIFIED);
            }else if(confirmationEmail.getType() == MailType.PASSWORD_RESET) {
                    throw new BadRequestException(ResultCode.TOKEN_RESET_PASSWORD_USED);
            }
        }

        if (confirmationEmail.isRevoked()
                ) {
            throw new BadRequestException(ResultCode.INVALID_TOKEN);
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

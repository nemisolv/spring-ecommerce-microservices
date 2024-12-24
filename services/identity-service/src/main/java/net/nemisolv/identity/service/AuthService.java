package net.nemisolv.identity.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.nemisolv.identity.payload.auth.*;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest authRequest);
    void registerExternal(RegisterRequest authRequest) throws MessagingException, NoSuchAlgorithmException;

    // this method is used only by the manager or admin to register a new employee, it is not exposed to the public
    void registerInternal(RegisterInternalRequest authRequest) ;
    void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException;

    void verifyEmail(String token) ;

    void forgotPassword(String email) throws NoSuchAlgorithmException;

    void resetPassword(ResetPasswordRequest reset) ;

    IntrospectResponse introspectToken(IntrospectRequest request);

    void resendEmailConfirmation(ResendEmailConfirmationRequest request);

    AuthenticationResponse getAuthResponse();
}


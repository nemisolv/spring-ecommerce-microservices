package net.nemisolv.identity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nemisolv.identity.payload.auth.*;
import net.nemisolv.identity.service.AuthService;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.lib.util.CommonUtil;
import net.nemisolv.lib.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthService authService;

    @Value("${app.secure.auth_secret}")
    private String authSecret;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest) {

        AuthenticationResponse response = authService.authenticate(authRequest);
        return ApiResponse.success(response);

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> register(@RequestBody @Valid RegisterRequest authRequest) throws MessagingException, NoSuchAlgorithmException {
        authService.registerExternal(authRequest);
        return ApiResponse.success();
    }

    @PostMapping("/verify-email/with-otp")
    public ApiResponse<Void> verifyEmailWithOtp(@RequestBody @Valid VerifyEmailWithOtpRequest request) throws NoSuchAlgorithmException {
        String token = CryptoUtil.sha256Hash(request.otp()+ authSecret);
         authService.verifyEmail(token);
         return ApiResponse.success();
    }

    @PostMapping("/verify-email/with-token")
    public ApiResponse<Void> verifyEmailWithToken(@RequestBody @Valid VerifyEmailWithTokenRequest request) {
        authService.verifyEmail(request.token());
        return ApiResponse.success();
    }


    // flow: client send a request to /forgot-password, then the server will send an email to the user with a token link or otp
    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) throws NoSuchAlgorithmException {
        authService.forgotPassword(forgotPasswordRequest.getEmail());
        return ApiResponse.success();
    }


    // handle for forgot password
    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ApiResponse.success();
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        authService.refreshToken(req, res);
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspectToken(@RequestBody IntrospectRequest request) {
        IntrospectResponse response = authService.introspectToken(request);
        return ApiResponse.success(response);
    }
}
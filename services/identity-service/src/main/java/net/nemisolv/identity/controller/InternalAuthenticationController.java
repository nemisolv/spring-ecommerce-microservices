package net.nemisolv.identity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.nemisolv.identity.payload.auth.AuthenticationResponse;
import net.nemisolv.identity.service.AuthService;
import net.nemisolv.lib.payload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-auth")
public class InternalAuthenticationController {

    private final AuthService authService;


//    @GetMapping("/auth-response")
//    public ApiResponse<AuthenticationResponse> getAuthResponse() {
//        AuthenticationResponse response = authService.getAuthResponse();
//        return ApiResponse.success(response);
//    }

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello guy!!");
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        authService.refreshToken(req, res);
    }
}

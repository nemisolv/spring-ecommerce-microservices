package net.nemisolv.identity.security.context;

import net.nemisolv.identity.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthContext {

    // Private constructor to prevent instantiation
    private AuthContext() {
        throw new UnsupportedOperationException("AuthContext is a utility class and cannot be instantiated.");
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return UserPrincipal of the authenticated user.
     * @throws IllegalStateException if no user is authenticated.
     */
    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new IllegalStateException("No authenticated user found.");
        }
        return (UserPrincipal) authentication.getPrincipal();
    }
}

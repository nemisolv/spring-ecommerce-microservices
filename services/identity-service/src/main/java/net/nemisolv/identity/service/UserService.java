package net.nemisolv.identity.service;

import net.nemisolv.identity.payload.auth.CreateUserRequest;
import net.nemisolv.identity.payload.user.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}

package net.nemisolv.identity.service;

import net.nemisolv.identity.payload.user.UserCreationRequest;
import net.nemisolv.identity.payload.user.UserResponse;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);
}

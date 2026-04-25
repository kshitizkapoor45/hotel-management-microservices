package com.kapoor.user.service.util;

import com.kapoor.user.service.dto.CompleteUserResponse;
import com.kapoor.user.service.dto.UserResponse;
import com.kapoor.user.service.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    public UserResponse mapToUser(User user){
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getKeycloakId()
        );
    }
}
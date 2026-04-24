package com.kapoor.user.service.services;

import com.kapoor.user.service.entities.User;
import com.kapoor.user.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSyncService {

    private final UserRepository userRepository;

    public User syncUser(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");

        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> createUser(keycloakId, email, name));
    }

    private User createUser(String keycloakId, String email, String name)  {
        try {
            User user = User.builder()
                    .keycloakId(keycloakId)
                    .email(email)
                    .name(name)
                    .build();

            return userRepository.save(user);

        } catch (Exception ex) {
            throw new RuntimeException("Error in saving user",ex);
        }
    }
}
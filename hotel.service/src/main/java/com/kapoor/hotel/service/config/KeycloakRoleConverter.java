package com.kapoor.hotel.service.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String CLIENT_ID = "oauth2-hotel-client";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey(CLIENT_ID)) {
            return List.of();
        }
        Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get(CLIENT_ID);
        List<String> roles = (List<String>) clientRoles.get("roles");
        return roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }
}
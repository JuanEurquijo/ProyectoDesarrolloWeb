package org.web.project.controller;

import java.util.Set;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// https://www.baeldung.com/spring-security-method-security
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/roles")
    ResponseEntity<Set<String>> roles(KeycloakAuthenticationToken authentication) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        return ResponseEntity.ok().body(account.getRoles());
    }

    // https://stackoverflow.com/a/69742920
    @GetMapping("/username")
    ResponseEntity<String> username(KeycloakAuthenticationToken authentication) {
        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        return ResponseEntity.ok().body(accessToken.getPreferredUsername());
    }
}

package br.com.joaopmazzo.multitenancy.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OauthIssuerResolver {

    private static final Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();
    private static final JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);

    private final JwtConverter jwtConverter;

    public JwtIssuerAuthenticationManagerResolver resolve() {
        List<String> issuers = new ArrayList<>();
        issuers.add("http://localhost:8080/realms/multitenancy");

        issuers.forEach(this::addManager);

        return authenticationManagerResolver;
    }

    private void addManager(String issuer) {
        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromOidcIssuerLocation(issuer));
        authenticationProvider.setJwtAuthenticationConverter(jwtConverter);
        authenticationManagers.put(issuer, authenticationProvider::authenticate);
    }

}
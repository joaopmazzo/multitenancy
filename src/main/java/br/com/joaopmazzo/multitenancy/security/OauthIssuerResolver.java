package br.com.joaopmazzo.multitenancy.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthIssuerResolver {

    @Value("${security.oauth2.issuer}")
    private String issuer;

    private final JwtConverter jwtConverter;

    public JwtIssuerAuthenticationManagerResolver resolve() {
        JwtAuthenticationProvider authenticationProvider =
                new JwtAuthenticationProvider(JwtDecoders.fromOidcIssuerLocation(issuer));
        authenticationProvider.setJwtAuthenticationConverter(jwtConverter);

        AuthenticationManager authenticationManager = authenticationProvider::authenticate;

        return new JwtIssuerAuthenticationManagerResolver(i -> issuer.equals(i) ? authenticationManager : null);
    }

}
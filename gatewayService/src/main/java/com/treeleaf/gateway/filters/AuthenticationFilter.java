package com.treeleaf.gateway.filters;


import com.treeleaf.gateway.exceptions.InvalidTokenException;
import com.treeleaf.gateway.util.JWTUtil;
import com.treeleaf.gateway.util.Util;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;


@Log4j2
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JWTUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = null;

            Optional<String> authHeaderOptional = Optional.ofNullable(exchange.getRequest()
                    .getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION) ?
                    exchange.getRequest()
                            .getHeaders()
                            .get(HttpHeaders.AUTHORIZATION)
                            .get(0) : null);

            if (authHeaderOptional.isPresent()) {
                String token = retrieveToken(authHeaderOptional);
                boolean isValid = isTokenValidHttpRequest(token);
                log.info("Token Valid Status: {}", isValid);

                if (isValid) {
                    Map<String, Object> claims = jwtUtil.parseClaims(token);
                    String username = claims.get(Claims.SUBJECT).toString();
                    String userId = claims.get("userId").toString();
                    String role = claims.get("role").toString();

                    serverHttpRequest = exchange.getRequest()
                            .mutate()
                            .header("id", userId)
                            .header("username", username)
                            .header("role", role)
                            .build();
                }
            }

            if (serverHttpRequest != null) {
                log.info("Mutating request by adding headers in filter. ");
                return chain.filter(exchange.mutate().request(serverHttpRequest).build());
            } else {
                log.info("By pass request without any check. ");
                return chain.filter(exchange);
            }
        });
    }

    private String retrieveToken(Optional<String> authHeader) throws InvalidTokenException {
        return authHeader.filter(header -> header.startsWith("Bearer"))
                .map(auth -> auth.replace("Bearer", "").trim())
                .orElseThrow(() -> new InvalidTokenException(Util.INVALID_TOKEN));
    }

    private boolean isTokenValidHttpRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return Boolean.TRUE.equals(restTemplate.
                exchange("http://localhost:8082/user/validate", HttpMethod.GET, entity, boolean.class).getBody());
    }

    public static class Config {

    }

}

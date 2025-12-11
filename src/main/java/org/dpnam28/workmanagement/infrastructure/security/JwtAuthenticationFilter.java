package org.dpnam28.workmanagement.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.RoleType;
import org.dpnam28.workmanagement.usecase.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtService.isTokenValid(token, username)) {
                Claims claims = jwtService.extractAllClaims(token);
                RoleType role = RoleType.valueOf(claims.get("role", String.class));
                PositionType position = null;
                Object positionObj = claims.get("position");
                if (positionObj != null) {
                    position = PositionType.valueOf(positionObj.toString());
                }
                Object idObject = claims.get("userId");
                Long userId = idObject != null ? Long.valueOf(idObject.toString()) : null;
                AuthenticatedUser principal = AuthenticatedUser.builder()
                        .id(userId)
                        .username(username)
                        .email(claims.get("email", String.class))
                        .role(role)
                        .position(position)
                        .build();
                var authority = new SimpleGrantedAuthority("ROLE_" + role.name());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(principal, null, Collections.singletonList(authority));
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            log.warn("JWT parsing failed: {}", ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}

package com.example.Demo_MultiTenant_MongoDB.Configuration.SecurityConfig;

import com.example.Demo_MultiTenant_MongoDB.Configuration.DatabaseConfig.ConnectionStorage;
import com.example.Demo_MultiTenant_MongoDB.Repository.TokenRepository;
import com.example.Demo_MultiTenant_MongoDB.Service.HeaderAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order()
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String TENANT_HEADER = "X-Tenant";
    private static final String CONNECTION_STRING = "mongodb://SkyLawson:RaidenIsEternal@mongo:27017/TENANT?authSource=admin&readPreference=primary";
    private static final String CENTRAL_CONNECTION_STRING = "mongodb://SkyLawson:RaidenIsEternal@mongo:27017/central?authSource=admin&readPreference=primary";
//    private static final String CONNECTION_STRING = "mongodb://localhost:27017/TENANT?readPreference=primary";
//    private static final String CENTRAL_CONNECTION_STRING = "mongodb://localhost:27017/central?readPreference=primary";
    private static final String TENANT_REPLACEMENT = "TENANT";

    private final HeaderAuthService headerAuthService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {
        String tenantHeader = request.getHeader(TENANT_HEADER);
        if(tenantHeader == null || tenantHeader.trim().isEmpty()) {
            ConnectionStorage.setConnection(CENTRAL_CONNECTION_STRING);
        } else {
            if(!headerAuthService.checkTenantExist(tenantHeader)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                String dbConnectionString = CONNECTION_STRING.replace(TENANT_REPLACEMENT, tenantHeader);
                ConnectionStorage.setConnection(dbConnectionString);
            }
        }
        if(request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            ConnectionStorage.clear();
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String tenantId;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            ConnectionStorage.clear();
            return;
        }
        jwt = authHeader.substring(7);
        tenantId = jwtService.extractTenantId(jwt);
        if(tenantHeader != null && !tenantHeader.equals(tenantId)) {
            filterChain.doFilter(request, response);
            ConnectionStorage.clear();
            return;
        }
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            boolean isTokenValid = tokenRepository.findByToken(jwt) != null;
            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
        ConnectionStorage.clear();
    }
}

package com.example.Demo_MultiTenant_MongoDB.Service;
import com.example.Demo_MultiTenant_MongoDB.Configuration.SecurityConfig.JwtService;
import com.example.Demo_MultiTenant_MongoDB.DTO.AuthenticationRequest;
import com.example.Demo_MultiTenant_MongoDB.DTO.AuthenticationResponse;
import com.example.Demo_MultiTenant_MongoDB.Model.Auth.Token;
import com.example.Demo_MultiTenant_MongoDB.Model.User;
import com.example.Demo_MultiTenant_MongoDB.DTO.RegisterRequest;
import com.example.Demo_MultiTenant_MongoDB.Model.Auth.Role;
import com.example.Demo_MultiTenant_MongoDB.Repository.TokenRepository;
import com.example.Demo_MultiTenant_MongoDB.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (userRepository.getUserByEmail(request.getEmail()) != null) {
            throw new IllegalStateException("User already exists");
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .occupation(request.getOccupation())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MANAGER)
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, String tenantId) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.getUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user, tenantId);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .build();
        tokenRepository.save(token);
    }
}

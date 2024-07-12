package com.example.Demo_MultiTenant_MongoDB.Controller;

import com.example.Demo_MultiTenant_MongoDB.DTO.AuthenticationRequest;
import com.example.Demo_MultiTenant_MongoDB.DTO.AuthenticationResponse;
import com.example.Demo_MultiTenant_MongoDB.DTO.RegisterRequest;
import com.example.Demo_MultiTenant_MongoDB.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            @RequestHeader(name = "X-Tenant", required = false) String tenantId
    ) {
        AuthenticationResponse authResponse = service.authenticate(request, tenantId);
        if(authResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(authResponse);
    }
}
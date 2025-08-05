package com.example.StainlesSteel.controllers;

import com.example.StainlesSteel.Tables.AuthRequest;
import com.example.StainlesSteel.component.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        // Authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtTokenUtil.generateAccessToken(userDetails);

        // Set cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false) // Enable in production with HTTPS
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .sameSite("Lax")
                .domain("localhost")// Helps with CSRF
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().body(Map.of(
                "message", "Login successful",
                "user", userDetails.getUsername()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Clear cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().body(Map.of("message", "Logout successful"));
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt", required = false) String token) {
        if (token != null && jwtTokenUtil.validateToken(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
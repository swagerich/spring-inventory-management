package com.erich.management.Controller;

import com.erich.management.Dto.Auth.AuthenticationRequest;
import com.erich.management.Dto.Auth.AuthenticationResponse;
import com.erich.management.Services.Auth.AppUserDetailsService;
import com.erich.management.Utils.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AppUserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        var jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }

  /*  @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
     var user = User.builder()
             .name(request.getFirstName())
             .lastName(request.getLastName())
             .email(request.getEmail())
             .password(passwordEncoder.encode(request.getPassword()))
             .roles(Collections.singletonList(new Roles(1L, "USER")));

    }*/
}

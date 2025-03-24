package com.ecommerce.app.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.constant.AppRole;
import com.ecommerce.app.domain.Role;
import com.ecommerce.app.domain.User;
import com.ecommerce.app.dto.LoginRequest;
import com.ecommerce.app.dto.MessageResponse;
import com.ecommerce.app.dto.SignupRequest;
import com.ecommerce.app.dto.UserInfoResponse;
import com.ecommerce.app.repository.RoleRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.security.jwt.JwtUtils;
import com.ecommerce.app.security.userdetails.UserDetailsImpl;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), roles, jwtCookie.toString());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                jwtCookie.toString())
                .body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        System.out.println("#################:: "+signUpRequest.getRoles());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setLastName(signUpRequest.getLastName());
        user.setMobileNumber(signUpRequest.getMobileNumber());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
    	Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN).get();
		roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    

}
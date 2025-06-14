package com.miniprojecttwo.controller;

import com.miniprojecttwo.entity.Role;
import com.miniprojecttwo.entity.User;
import com.miniprojecttwo.config.CustomUserDetailsService;
import com.miniprojecttwo.config.JwtUtil;
import com.miniprojecttwo.repository.RoleRepository;
import com.miniprojecttwo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.get("username"), req.get("password")));
            UserDetails user = userService.loadUserByUsername(req.get("username"));

            User ForUserId = userRepository.findByUsername(req.get("username"))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtil.generateToken(user, ForUserId.getUserid());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        try {
            // Check if username already exists
            if (userRepository.findByUsername(req.get("username")).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
            }

            // Encrypt password using BCrypt
            String hashedPassword = passwordEncoder.encode(req.get("password"));

            // Create new user
            User user = new User();
            user.setUserid(req.get("userid"));
            user.setUsername(req.get("username"));
            user.setPassword(hashedPassword);

            // Assign default role (e.g., "ROLE_USER")
            Role role = roleRepository.findByName(req.get("user_role"))
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRoles(List.of(role)); // ✅ Assign role

            // Save user
            userRepository.save(user);

            UserDetails reguser = userService.loadUserByUsername(req.get("username"));
            String token = jwtUtil.generateToken(reguser, user.getUserid());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }


}

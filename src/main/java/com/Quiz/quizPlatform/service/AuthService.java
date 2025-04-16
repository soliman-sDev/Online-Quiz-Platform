package com.Quiz.quizPlatform.service;

import com.Quiz.quizPlatform.dto.AuthResponse;
import com.Quiz.quizPlatform.entity.User;
import com.Quiz.quizPlatform.repository.UserRepository;
import com.Quiz.quizPlatform.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(AuthResponse Auth){
        AuthResponse authRes = new AuthResponse();
        try {
            User user = new User();
            user.setUsername(Auth.getUsername());
            user.setPassword(passwordEncoder.encode(Auth.getPassword()));
            user.setEmail(Auth.getEmail());
            user.setRole(Auth.getRole());
            User ourUserResult = userRepository.save(user);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                authRes.setUser(ourUserResult);
                authRes.setMessage("User Saved Successfully");
                authRes.setStatusCode(200);
            }
        }catch (Exception e){
            authRes.setStatusCode(500);
            authRes.setError(e.getMessage());
        }
        return authRes;
    }

    public AuthResponse login(AuthResponse Auth){
        AuthResponse authRes = new AuthResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(Auth.getUsername(),Auth.getPassword()));
            var user = userRepository.findByUsername(Auth.getUsername()).orElseThrow();
            System.out.print("User is "+ user);
            var jwt = jwtUtil.generateToken(user);
            authRes.setStatusCode(200);
            authRes.setToken(jwt);
            authRes.setExpirationTime("24Hr");
            authRes.setMessage("Successfully Signed In");
        }catch (Exception e){
            authRes.setStatusCode(500);
            authRes.setError(e.getMessage());
        }
        return authRes;
    }


}

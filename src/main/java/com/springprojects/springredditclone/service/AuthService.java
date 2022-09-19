package com.springprojects.springredditclone.service;

import com.springprojects.springredditclone.dto.RegisterRequest;
import com.springprojects.springredditclone.model.User;
import com.springprojects.springredditclone.model.VerificationToken;
import com.springprojects.springredditclone.repository.UserRepository;
import com.springprojects.springredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    //its better to use constructor injection than field injection

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest)
    {
        User user =new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token=generateVerificationToken(user);

    }

    private String generateVerificationToken(User user) {
       String token= UUID.randomUUID().toString();
        VerificationToken verificationToken= new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}


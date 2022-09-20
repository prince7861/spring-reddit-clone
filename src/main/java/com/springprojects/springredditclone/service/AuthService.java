package com.springprojects.springredditclone.service;

import com.springprojects.springredditclone.dto.LoginRequest;
import com.springprojects.springredditclone.dto.RegisterRequest;
import com.springprojects.springredditclone.exceptions.SpringRedditException;
import com.springprojects.springredditclone.model.NotificationEmail;
import com.springprojects.springredditclone.model.User;
import com.springprojects.springredditclone.model.VerificationToken;
import com.springprojects.springredditclone.repository.UserRepository;
import com.springprojects.springredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    //it's better to use constructor injection than field injection

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
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
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to your Spring Reddit, "+
                "please click on the below url to activate your account:"+
                "http://localhost:8080/api/auth/accountVerification/" +token));
    }

    private String generateVerificationToken(User user) {
       String token= UUID.randomUUID().toString();
        VerificationToken verificationToken= new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
       verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
       fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username=verificationToken.getUser().getUsername();
    User user= userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("User not found with name-"+username));
    user.setEnabled(true);
    userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}


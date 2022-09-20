package com.springprojects.springredditclone.controller;

import com.springprojects.springredditclone.dto.LoginRequest;
import com.springprojects.springredditclone.dto.RegisterRequest;
import com.springprojects.springredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
   private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
    {
    authService.signup(registerRequest);
    return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);

    }
    @GetMapping("accountVerification/{token}")
 public ResponseEntity<String> verifyAccount(@PathVariable String token)
    {
     authService.verifyAccount(token);
     return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }
    @PostMapping("/logout")
    public void login(@RequestBody LoginRequest loginRequest)
    {
        authService.login(loginRequest);
    }
}
package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.entities.User;
import com.vmo.vmoproject.exception.BadRequestException;
import com.vmo.vmoproject.exception.Errors;
import com.vmo.vmoproject.request.AccountLogin;
import com.vmo.vmoproject.request.AccountRegister;
import com.vmo.vmoproject.response.JwtResponse;
import com.vmo.vmoproject.security.jwt.JwtProvider;
import com.vmo.vmoproject.security.userprincal.UserPrinciple;
import com.vmo.vmoproject.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/account")
public class AccountController {
    final
    UserService userService;
    final
    PasswordEncoder passwordEncoder;
    final
    JwtProvider jwtProvider;
    final
    AuthenticationManager authenticationManager;

    public AccountController(UserService userService, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> register(@Valid @RequestBody AccountRegister formLogin) {
        if (userService.existsByUsername(formLogin.getUsername())) {
            throw new BadRequestException(List.of(new Errors("username already exists")));
        }
        if (userService.existsByEmail(formLogin.getEmail())) {
            throw new BadRequestException(List.of(new Errors("email already exists")));
        }
        Set<String> roles = new HashSet<>();
        for (String str : formLogin.getRoleName()) {
            if (str.equals("ADMIN") || str.equals("USER")) {
                roles.add(str);
            } else {
                throw new RuntimeException("role not found");
            }
        }
        User user = new User(formLogin.getName(), formLogin.getUsername(), formLogin.getEmail(), passwordEncoder.encode(formLogin.getPassword()), roles);
    return ResponseEntity.ok().body(userService.saveUser(user));
    }
    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> loginA(@Valid @RequestBody AccountLogin login){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
return ResponseEntity.ok().body(new JwtResponse(token,userPrinciple.getName(),userPrinciple.getAuthorities()));
    }
}

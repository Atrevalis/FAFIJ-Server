package fafij.server.controllers;

import fafij.server.security.AuthRequest;
import fafij.server.security.AuthResponse;
import fafij.server.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtTokeUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest){
        Authentication authentication;
        try{
            authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
            System.out.println(authentication);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login or Password wrong!", e);
        }
        String jwt = jwtTokeUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }
}

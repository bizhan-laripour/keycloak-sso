package com.sso.keycloak.controller;

import com.sso.keycloak.dto.UserCredentials;
import com.sso.keycloak.service.KeycloakService;
import com.sso.keycloak.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(value = "login" , method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials){

        return new ResponseEntity<>(loginService.loginResponse(userCredentials), HttpStatus.OK);
    }
}

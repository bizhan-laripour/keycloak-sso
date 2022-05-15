package com.sso.keycloak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.keycloak.dto.SsoResponse;
import com.sso.keycloak.dto.UserCredentials;
import com.sso.keycloak.exception.CustomException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    private KeycloakService keycloakService;


    @Autowired
    public LoginService(KeycloakService keycloakService){
        this.keycloakService = keycloakService;
    }


    public SsoResponse loginResponse(UserCredentials userCredentials){
        SsoResponse ssoResponse = new SsoResponse();
        String token = keycloakService.getToken(userCredentials);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> map = objectMapper.readValue(token, Map.class);
            ssoResponse.setAccessToken(map.get("access_token"));
            ssoResponse.setModules(keycloakService.getRolesOfAUser(userCredentials));
            ssoResponse.setStatus("SUCCESS");
            ssoResponse.setStatusMessage("Request has been processed successfully");
            return ssoResponse;
        }catch (Exception exception){
            throw new CustomException(HttpStatus.SC_BAD_REQUEST , "login failed");
        }

    }
}

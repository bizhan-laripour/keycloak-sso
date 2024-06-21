package com.sso.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class  Main {


    @Value("${keycloak.credentials.secret}")
    private String SECRETKEY;

    @Value("${keycloak.resource}")
    private String CLIENTID;

    @Value("${keycloak.auth-server-url}")
    private String AUTHURL;

    @Value("${keycloak.realm}")
    private String REALM;
    @Value("${username}")
    private String USERNAME;
    @Value("${password}")
    private String PASSWORD;

    public static void main(String[] args) {
        SpringApplication.run(Main.class , args);
    }


    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder().serverUrl(AUTHURL)
                .realm(REALM)
                .clientId(CLIENTID)
                .username("admin")
                .password("123")
//                .password("123!@#qwe")
                .grantType("password")
                .clientSecret(SECRETKEY)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
    }



}

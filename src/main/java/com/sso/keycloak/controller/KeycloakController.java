package com.sso.keycloak.controller;

import com.sso.keycloak.dto.AssignRoleDto;
import com.sso.keycloak.dto.UserCredentials;
import com.sso.keycloak.dto.UserDTO;
import com.sso.keycloak.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/keycloak")
@CrossOrigin("*")
public class KeycloakController {



    private final KeycloakService keycloakService;

    @Autowired
    public KeycloakController(KeycloakService keycloakService){
        this.keycloakService = keycloakService;
    }

    @RequestMapping(value = "assign-roles", method = RequestMethod.POST)
    public ResponseEntity<?> assignRoles(@RequestBody AssignRoleDto assignRoleDto){

        return new ResponseEntity<>(keycloakService.assignRoleToUser(assignRoleDto) , HttpStatus.OK);
    }


    @RequestMapping(value = "get-all-roles" , method = RequestMethod.POST)
    public ResponseEntity<?> getAllRoles(){
        return new ResponseEntity<>(keycloakService.getAllRoles() , HttpStatus.OK);
    }

    @RequestMapping(value = "create-user" , method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(keycloakService.createUserInKeyCloak(userDTO) , HttpStatus.OK);
    }

    @RequestMapping(value = "login" , method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials){
        return new ResponseEntity<>(keycloakService.getToken(userCredentials) , HttpStatus.OK);
    }
    @RequestMapping(value = "delete-user" , method = RequestMethod.POST)
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(keycloakService.deleteKeycloakUser(userDTO) , HttpStatus.OK);
    }

    @RequestMapping(value = "reset-password" , method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@RequestBody UserCredentials userCredentials){
        return new ResponseEntity<>(keycloakService.resetPassword(userCredentials) , HttpStatus.OK);
    }

    @RequestMapping(value = "add-role" , method = RequestMethod.POST)
    public ResponseEntity<?> addRealmRole(@RequestParam(value = "role") String roleName){
        return new ResponseEntity<>(keycloakService.addRealmRole(roleName) , HttpStatus.OK);
    }

    /**
     * this endpoint doesnt work
     * @param userCredentials
     * @return
     */
    @RequestMapping(value = "get-user-roles" , method = RequestMethod.POST)
    public ResponseEntity<?> getRolesOfUser(@RequestBody UserCredentials userCredentials){
        return new ResponseEntity<>(keycloakService.getRolesOfUser(userCredentials) , HttpStatus.OK);
    }


    @RequestMapping(value = "update-user" , method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(keycloakService.updateUser(userDTO) , HttpStatus.OK);
    }

    @RequestMapping(value = "update-role" , method = RequestMethod.POST)
    public ResponseEntity<?> updateRealmRole(@RequestParam(value = "previous-role") String previousRole ,@RequestParam(value = "next-role") String nextRole){
        return new ResponseEntity<>(keycloakService.updateRealmRole(previousRole , nextRole) , HttpStatus.OK);
    }


    @RequestMapping(value = "general-create-user" , method = RequestMethod.POST)
    public ResponseEntity<?> generalCreateUser(UserDTO userDTO){
        return null;
    }

    @RequestMapping(value = "get-all-users" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(keycloakService.getAllUsers() , HttpStatus.OK);
    }

    @RequestMapping(value = "get-user" , method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@RequestParam(value = "username") String username){
        return new ResponseEntity<>(keycloakService.getUserById(username) , HttpStatus.OK);
    }
    @RequestMapping(value = "enable-disable" , method = RequestMethod.POST)
    public ResponseEntity<?> enableDisableUser(@RequestParam(value = "username") String username , @RequestParam(value = "enable") String enable){
        return new ResponseEntity<>(keycloakService.enableDisableUser(username , Boolean.parseBoolean(enable)) , HttpStatus.OK);
    }


}

package com.sso.keycloak.controller;

import com.sso.keycloak.dto.*;
import com.sso.keycloak.exception.CustomException;
import com.sso.keycloak.service.KeycloakService;
import com.sso.keycloak.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/keycloak")
@CrossOrigin("*")
public class KeycloakController {


    private final KeycloakService keycloakService;
    private final LoginService loginService;

    @Autowired
    public KeycloakController(KeycloakService keycloakService , LoginService loginService) {
        this.keycloakService = keycloakService;
        this.loginService = loginService;
    }

    /**
     * this api assigns realm role to a user in keycloak
     * or assign workgroup class to a user
     * @param assignRoleDto
     * @return
     */
    @RequestMapping(value = "assign-work-group-class-to-user", method = RequestMethod.POST)
    public ResponseEntity<?> assigWorkGroupClassToUser(@RequestBody AssignRoleDto assignRoleDto) {
        return new ResponseEntity<>(keycloakService.assignRealmRoleToUser(assignRoleDto), HttpStatus.OK);
    }


    /**
     * this api gets all realm roles
     * or get all work group classes in realm
     * @return
     */
    @RequestMapping(value = "get-all-work-group-classes", method = RequestMethod.GET)
    public ResponseEntity<?> getAllWorkGroupClasses() {
        return new ResponseEntity<>(keycloakService.getAllRealmRoles(), HttpStatus.OK);
    }

    /**
     * this method creates a user in keycloak
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "create-user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(keycloakService.createUserInKeyCloak(userDTO), HttpStatus.OK);
    }

    /**
     * this method is login
     * @param userCredentials
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {
        return new ResponseEntity<>(loginService.loginResponse(userCredentials), HttpStatus.OK);
    }

    /**
     * this api is for deleting the user
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "delete-user", method = RequestMethod.POST)
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(keycloakService.deleteKeycloakUser(userDTO), HttpStatus.OK);
    }

    /**
     * this api is for reset password
     * @param userCredentials
     * @return
     */
    @RequestMapping(value = "reset-password", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@RequestBody UserCredentials userCredentials) {
        return new ResponseEntity<>(keycloakService.resetPassword(userCredentials), HttpStatus.OK);
    }

    /**
     * this api is for adding work group class or in keycloak add realm role
     * @param roleName
     * @return
     */
    @RequestMapping(value = "add-work-group-class", method = RequestMethod.POST)
    public ResponseEntity<?> addWorkGroupClass(@RequestParam(value = "role") String roleName) {
        return new ResponseEntity<>(keycloakService.addRealmRole(roleName), HttpStatus.OK);
    }

    /**
     * this method is for adding client role in keycloak
     * or adding privilege for a module
     * @param clientId
     * @param role
     * @return
     */
    @RequestMapping(value = "add-client-role" , method = RequestMethod.POST)
    public ResponseEntity<?> addClientRole(@RequestParam(value = "client_id") String clientId , @RequestParam(value = "role") String role){
        return new ResponseEntity<>(keycloakService.addClientRole(clientId , role) , HttpStatus.OK);
    }

    /**
     * this api is for assign client role or module privilege to a user
     * @param assignClientRoleToUserDto
     * @return
     */
    @RequestMapping(value = "assign-client-role-to-user" , method = RequestMethod.POST)
    public ResponseEntity<?> assignClientRoleToUser(@RequestBody AssignClientRoleToUserDto assignClientRoleToUserDto){
        return new ResponseEntity<>(keycloakService.assignClientRoleToUser(assignClientRoleToUserDto) , HttpStatus.OK);
    }


    /**
     * this api is for updating a user
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "update-user", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        List<String> stringList = new ArrayList<>();
        return new ResponseEntity<>(keycloakService.updateUser(userDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "update-role", method = RequestMethod.POST)
    public ResponseEntity<?> updateRealmRole(@RequestParam(value = "previous-role") String previousRole, @RequestParam(value = "next-role") String nextRole) {
        System.out.println("");
        return new ResponseEntity<>(keycloakService.updateRealmRole(previousRole, nextRole), HttpStatus.OK);
    }


    @RequestMapping(value = "general-create-user", method = RequestMethod.POST)
    public ResponseEntity<?> generalCreateUser(UserDTO userDTO) {
        return null;
    }

    @RequestMapping(value = "get-all-users", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(keycloakService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "get-user", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@RequestParam(value = "username") String username) {
        return new ResponseEntity<>(keycloakService.getUserById(username), HttpStatus.OK);
    }

    @RequestMapping(value = "enable-disable", method = RequestMethod.POST)
    public ResponseEntity<?> enableDisableUser(@RequestParam(value = "username") String username, @RequestParam(value = "enable") String enable) {
        return new ResponseEntity<>(keycloakService.enableDisableUser(username, Boolean.parseBoolean(enable)), HttpStatus.OK);
    }

    @RequestMapping(value = "get-all-work-groups", method = RequestMethod.GET)
    public ResponseEntity<?> getAllWorkGroups() {
        try {
            return new ResponseEntity<>(keycloakService.getAllGroups(), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException(500, exception.getMessage());
        }
    }

    @RequestMapping(value = "create-group", method = RequestMethod.POST)
    public ResponseEntity<?> createGroup(@RequestParam(value = "group") String group) {
        try {
            return new ResponseEntity<>(keycloakService.createGroup(group), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException(500, exception.getMessage());
        }
    }

    @RequestMapping(value = "assign-roles-to-group", method = RequestMethod.POST)
    public ResponseEntity<?> assignRolesToGroup(@RequestBody AssignRolesToGroupDto assignRolesToGroupDto) {
        try {
            return new ResponseEntity<>(keycloakService.assignRolesToGroup(assignRolesToGroupDto), HttpStatus.OK);
        }catch (Exception exception){
            throw new CustomException(500 , exception.getMessage());
        }
    }

    @RequestMapping(value = "assign-user-to-groups" , method = RequestMethod.POST)
    public ResponseEntity<?> assignUsersToGroup(@RequestBody AssignUserToGroups assignUserToGroups){
        try{
            return new ResponseEntity<>(keycloakService.assignUsersToGroup(assignUserToGroups) , HttpStatus.OK);
        }catch (Exception exception){
            throw new CustomException(500 , exception.getMessage());
        }
    }



}

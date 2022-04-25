package com.sso.keycloak.service;

import com.sso.keycloak.dto.*;
import com.sso.keycloak.exception.CustomException;
import com.sso.keycloak.mapper.UserMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeycloakService {


    @Value("${keycloak.credentials.secret}")
    private String SECRETKEY;

    @Value("${keycloak.resource}")
    private String CLIENTID;

    @Value("${keycloak.auth-server-url}")
    private String AUTHURL;

    @Value("${keycloak.realm}")
    private String REALM;

    private final Keycloak keycloak;

    private UserMapper userMapper;


    @Autowired
    public KeycloakService(Keycloak keycloak, UserMapper userMapper) {
        this.keycloak = keycloak;
        this.userMapper = userMapper;

    }

    public String assignRoleToUser(AssignRoleDto assignRoleDto) {
        String userId = keycloak
                .realm(REALM)
                .users()
                .search(assignRoleDto.getUsername())
                .get(0)
                .getId();
        UserResource user = keycloak
                .realm(REALM)
                .users()
                .get(userId);
        List<RoleRepresentation> roleToAdd = new LinkedList<>();
        roleToAdd.add(keycloak
                .realm(REALM)
                .roles()
                .get(assignRoleDto.getRole())
                .toRepresentation()
        );
        user.roles().realmLevel().add(roleToAdd);
        return "role added to user";
    }


    public List<String> getAllRoles() {
        ClientRepresentation clientRep = keycloak
                .realm(REALM)
                .clients()
                .findByClientId(CLIENTID)
                .get(0);
        List<String> availableRoles = keycloak
                .realm(REALM)
                .clients()
                .get(clientRep.getId())
                .roles()
                .list()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        return availableRoles;
    }


    public UserDTO createUserInKeyCloak(UserDTO userDTO) {
        int statusId = 0;
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource userRessource = realmResource.users();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmailAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEnabled(true);
        Map<String, List<String>> attr = generateAttributes(userDTO);
        user.setAttributes(attr);
        // Create user
        Response result = userRessource.create(user);
        System.out.println("Keycloak create user response code>>>>" + result.getStatus());
        statusId = result.getStatus();
        if (statusId == 201) {
            String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            System.out.println("User created with userId:" + userId);
            // Define password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userDTO.getPassword());
            // Set password credential
            userRessource.get(userId).resetPassword(passwordCred);

            RoleRepresentation savedRoleRepresentation = realmResource.roles().get("user").toRepresentation();
            realmResource.users().get(userId).roles().realmLevel().add(Arrays.asList(savedRoleRepresentation));
            System.out.println("Username==" + userDTO.getUsername() + " created in keycloak successfully");
            return userDTO;
        } else if (statusId == 409) {
            throw new CustomException(409, "the user is currently exists");
        } else {
            throw new CustomException(statusId, "the user could not be created in keycloak");
        }
    }

    public String getByRefreshToken(String refreshToken) {
        String responseToken = null;
        try {
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
            urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
            urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken));
            urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));
            responseToken = sendPost(urlParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseToken;
    }

    public String getToken(UserCredentials userCredentials) {
        String responseToken = null;
        try {
            String username = userCredentials.getUsername();
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("grant_type", "password"));
            urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", userCredentials.getPassword()));
            urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));
            responseToken = sendPost(urlParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseToken;

    }

    private String sendPost(List<NameValuePair> urlParameters) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(AUTHURL + "/realms/" + REALM + "/protocol/openid-connect/token");
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }


    public String deleteKeycloakUser(UserDTO userDTO) {
        List<UserRepresentation> userList = keycloak.realm(REALM).users().search(userDTO.getUsername());
        for (UserRepresentation user : userList) {
            if (user.getUsername().equals(userDTO.getUsername())) {
                keycloak.realm(REALM).users().delete(user.getId());
            }
        }
        return "the user deleted successfully";
    }

    public List<UserDTO> getAllUsers() {
        List<UserRepresentation> userRepresentations = keycloak.realm(REALM).users().list();
        List<UserDTO> users = new ArrayList<>();
        userRepresentations.forEach(s -> users.add(userMapper.toUserDto(s)));
        return users;
    }


    public String resetPassword(UserCredentials userCredentials) {

        String userId = keycloak
                .realm(REALM)
                .users()
                .search(userCredentials.getUsername())
                .get(0)
                .getId();
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource userRessource = realmResource.users();

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userCredentials.getPassword().trim());

        // Set password credential
        userRessource.get(userId).resetPassword(passwordCred);
        return "password changed successfully";
    }

    public String addRealmRole(String role) {
        if (!getAllRoles().contains(role)) {
            RoleRepresentation roleRep = new RoleRepresentation();
            roleRep.setName(role);
            roleRep.setDescription("role_" + role);
            keycloak.realm(REALM).roles().create(roleRep);
        }
        return "role added to keycloak";
    }


    public String updateRealmRole(String previousRole, String nextRole) {
        if (!getAllRoles().contains(nextRole)) {
            RoleRepresentation roleRep = new RoleRepresentation();
            roleRep.setName(nextRole);
            roleRep.setDescription("role_" + nextRole);
            keycloak.realm(REALM).roles().get(previousRole).update(roleRep);
        }
        return "role updated successfully";
    }

    public List<String> getRolesOfUser(UserCredentials userCredentials) {
        Optional<UserRepresentation> user = keycloak.realm(REALM).users().search(userCredentials.getUsername()).stream()
                .filter(u -> u.getUsername().equals(userCredentials.getUsername())).findFirst();
        if (user.isPresent()) {
            UserRepresentation userRepresentation = user.get();
            return userRepresentation.getRealmRoles();
        }
        return null;
    }

    public String updateUser(UserDTO userDTO) {

        Optional<UserRepresentation> user = keycloak.realm(REALM).users().search(userDTO.getUsername()).stream()
                .filter(u -> u.getUsername().equals(userDTO.getUsername())).findFirst();
        if (user.isPresent()) {
            UserRepresentation userRepresentation = user.get();
            UserResource userResource = keycloak.realm(REALM).users().get(userRepresentation.getId());
            Map<String, List<String>> attributes = new HashMap<>();
            attributes.put("description", Arrays.asList(userDTO.getUsername() + " updated in" + new Date()));
            userRepresentation.setAttributes(attributes);
            userRepresentation.setEmail(userDTO.getEmailAddress());
            userRepresentation.setFirstName(userDTO.getFirstName());
            userRepresentation.setLastName(userDTO.getLastName());
            userResource.update(userRepresentation);
            return "user updated successfully";
        } else {
            return "user not updated";
        }
    }

    public UserDTO getUserById(String userName) {
        UserRepresentation userRepresentation = keycloak.realm(REALM).users().search(userName).get(0);
        return userMapper.toUserDto(userRepresentation);
    }

    private Map<String, List<String>> generateAttributes(UserDTO userDTO) {
        Map<String, List<String>> att = new HashMap<>();
        List<String> telephone = new ArrayList<>();
        telephone.add(userDTO.getTelephone());
        List<String> preferredLocal = new ArrayList<>();
        preferredLocal.add(userDTO.getPreferredLocal());
        List<String> accessibleViaMobile = new ArrayList<>();
        accessibleViaMobile.add(userDTO.getAccessibleViaMobile());
        List<String> fax = new ArrayList<>();
        fax.add(userDTO.getFax());
        List<String> mobile = new ArrayList<>();
        mobile.add(userDTO.getMobile());
        List<String> profile = new ArrayList<>();
        profile.add(userDTO.getProfile());
        List<String> accessProfile = new ArrayList<>();
        accessProfile.add(userDTO.getAccessProfile());
        List<String> notificationPreference = new ArrayList<>();
        notificationPreference.add(userDTO.getNotificationPreference());
        att.put("telephone", telephone);
        att.put("preferredLocal", preferredLocal);
        att.put("accessibleViaMobile", accessibleViaMobile);
        att.put("fax", fax);
        att.put("mobile", mobile);
        att.put("profile", profile);
        att.put("accessProfile", accessProfile);
        att.put("notificationPreference", notificationPreference);
        return att;
    }


    public String enableDisableUser(String username, boolean enable) {
        UserRepresentation userRepresentation = keycloak.realm(REALM).users().search(username).get(0);

        if (userRepresentation != null) {
            UserResource userResource = keycloak.realm(REALM).users().get(userRepresentation.getId());
            userRepresentation.setEnabled(enable);
            userResource.update(userRepresentation);
            return "user enabled is " + enable;
        } else {
            return "user not found";
        }
    }


    public List<GroupRepresentation> getAllGroups() {
        return keycloak.realm(REALM).groups().groups();
    }

    public String createGroup(String name) {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(name);
        keycloak
                .realm(REALM)
                .groups()
                .add(group);
        return "the group created";
    }

    public String assignRolesToGroup(AssignRolesToGroupDto assignRolesToGroupDto) {
        for (String obj : assignRolesToGroupDto.getRoles()) {
            RoleRepresentation groupRole = keycloak.realm(REALM).roles().get(obj).toRepresentation();
            List<RoleRepresentation> list = new ArrayList<>();
            list.add(groupRole);
            keycloak.realm(REALM).groups().group(assignRolesToGroupDto.getGroupId()).roles().realmLevel().add(list);
        }
        return "roles added to the group";
    }

    public String assignUsersToGroup(AssignUserToGroups assignUserToGroups) {
        Optional<UserRepresentation> user = keycloak.realm(REALM).users().search(assignUserToGroups.getUsername()).stream()
                .filter(u -> u.getUsername().equals(assignUserToGroups.getUsername())).findFirst();
        if (user.isPresent()) {
            for (String groupId : assignUserToGroups.getGroupsId()) {
                GroupRepresentation groupRepresentation = keycloak.realm(REALM).groups().group(groupId).toRepresentation();
                user.get().setGroups(assignUserToGroups.getGroupsId());
            }
            return "user updated successfully";
        } else {
            return "user not updated";
        }
    }

}

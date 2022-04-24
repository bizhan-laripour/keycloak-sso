package com.sso.keycloak.mapper;

import com.sso.keycloak.dto.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserMapper {

    public UserDTO toUserDto(UserRepresentation userRepresentation) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(userRepresentation.getFirstName());
        userDTO.setLastName(userRepresentation.getLastName());
        userDTO.setUsername(userRepresentation.getUsername());
        userDTO.setEmailAddress(userRepresentation.getEmail());
        Map<String, List<String>> attr = userRepresentation.getAttributes();
        if (attr != null && attr.size() > 0) {
            if (attr.get("accessibleViaMobile") != null) {
                userDTO.setAccessibleViaMobile(attr.get("accessibleViaMobile").get(0));
            } else {
                userDTO.setAccessibleViaMobile("");
            }
            if (attr.get("telephone") != null) {
                userDTO.setTelephone(attr.get("telephone").get(0));
            } else {
                userDTO.setTelephone("");
            }
            if (attr.get("preferredLocal") != null) {
                userDTO.setPreferredLocal(attr.get("preferredLocal").get(0));
            } else {
                userDTO.setPreferredLocal("");
            }
            if (attr.get("fax") != null) {
                userDTO.setFax(attr.get("fax").get(0));
            } else {
                userDTO.setFax("");
            }
            if (attr.get("mobile") != null) {
                userDTO.setMobile(attr.get("mobile").get(0));
            } else {
                userDTO.setMobile("");
            }
            if (attr.get("profile") != null) {
                userDTO.setProfile(attr.get("profile").get(0));
            } else {
                userDTO.setProfile("");
            }
            if (attr.get("accessProfile") != null) {
                userDTO.setAccessProfile(attr.get("accessProfile").get(0));
            } else {
                userDTO.setAccessProfile("");
            }
            if (attr.get("notificationPreference") != null) {
                userDTO.setNotificationPreference(attr.get("notificationPreference").get(0));
            } else {
                userDTO.setNotificationPreference("");
            }
        }
        return userDTO;
    }

}

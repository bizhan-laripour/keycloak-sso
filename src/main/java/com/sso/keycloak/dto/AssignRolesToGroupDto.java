package com.sso.keycloak.dto;

import java.util.List;

public class AssignRolesToGroupDto {

    private String groupId;
    private List<String> roles;



    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}

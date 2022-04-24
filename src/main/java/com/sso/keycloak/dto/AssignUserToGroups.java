package com.sso.keycloak.dto;

import java.util.List;

public class AssignUserToGroups {

    private String username;

    private List<String> groupsId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(List<String> groupsId) {
        this.groupsId = groupsId;
    }
}

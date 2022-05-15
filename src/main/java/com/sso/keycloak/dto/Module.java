package com.sso.keycloak.dto;

import java.util.Date;
import java.util.List;

public class Module {

    private Boolean available;
    private String moduleName;

    private String moduleDescription;

    private String moduleIdStr;

    private int selectionCount;

    private Date lastUsageDate;

    private List<ModulePrivilege> modulePrivileges;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getModuleIdStr() {
        return moduleIdStr;
    }

    public void setModuleIdStr(String moduleIdStr) {
        this.moduleIdStr = moduleIdStr;
    }

    public int getSelectionCount() {
        return selectionCount;
    }

    public void setSelectionCount(int selectionCount) {
        this.selectionCount = selectionCount;
    }

    public Date getLastUsageDate() {
        return lastUsageDate;
    }

    public void setLastUsageDate(Date lastUsageDate) {
        this.lastUsageDate = lastUsageDate;
    }

    public List<ModulePrivilege> getModulePrivileges() {
        return modulePrivileges;
    }

    public void setModulePrivileges(List<ModulePrivilege> modulePrivileges) {
        this.modulePrivileges = modulePrivileges;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}

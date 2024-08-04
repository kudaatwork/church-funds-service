package com.tithe_system.tithe_management_system.utils.requests;

import java.util.List;

public class RemoveUserRolesFromUserGroupRequest {
    private List<Long> userRoleIds;
    private Long userGroupId;

    public List<Long> getUserRoleIds() {
        return userRoleIds;
    }

    public void setUserRoleIds(List<Long> userRoleIds) {
        this.userRoleIds = userRoleIds;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Override
    public String toString() {
        return "RemoveUserRolesFromUserGroupRequest{" +
                "userRoleIds=" + userRoleIds +
                ", userGroupId=" + userGroupId +
                '}';
    }
}

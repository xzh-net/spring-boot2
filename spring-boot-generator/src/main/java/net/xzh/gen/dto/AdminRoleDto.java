package net.xzh.gen.dto;

import java.util.List;

import net.xzh.gen.model.UmsAdmin;
import net.xzh.gen.model.UmsRole;

/**
 * Created 2020/12/9.
 */
public class AdminRoleDto extends UmsAdmin {

    private List<UmsRole> roleList;

    public List<UmsRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UmsRole> roleList) {
        this.roleList = roleList;
    }
}

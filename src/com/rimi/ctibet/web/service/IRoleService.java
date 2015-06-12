package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.RoleAccess;

public interface IRoleService {
	
	public void saveRole(Role role);
	public void updateRole(Role role);
    public void deleteRole(Role role);
    public Role getRoleBuCode(String code);
    //获取到所有角色
    public List<Role> getRoles();
    //为角色添加权限
    public void saveAccessForRole(RoleAccess roleAccess);
    //获取到所有角色及旗下权限
    public Pager getAllRolesInfo(Pager pager,String keyWord);
    //根据role的code获取全部权限
    public List<Map<String,Object>> getAllAccessByRoleCode(String roleCode);
    //判断角色名是否是重复
    public boolean isRoleNameRepeat(String roleName,String code);
}

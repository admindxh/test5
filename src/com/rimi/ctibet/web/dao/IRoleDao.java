package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.domain.Role;

public interface IRoleDao extends BaseDao<Role>{
	//根据角色 code 获取角色
	public Role findByCode(String code);
	//根据角色code获得权限集
	public List<String> getAccessByRoleCode(String code); 
    //给角色增加权限
	public void saveAccess(Access access,Role role);
	//=====================================================================================
	//删除角色并删除旗下权限
	public void deleteRole(Role role);
	//修改角色，先删除旗下所有权限，再把新的添加进去
	public void updateRole(Role role);
	 //获取到所有角色及旗下权限
    public Pager getAllRolesInfo(Pager pager,String keyWord);
  //根据role的code获取全部权限
    public List<Map<String,Object>> getAllAccessByRoleCode(String roleCode);
    //判断角色名是否是重复
    public boolean isRoleNameRepeat(String roleName,String code);
}

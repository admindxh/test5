package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.RoleAccess;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.dao.IRoleAccessDao;
import com.rimi.ctibet.web.dao.IRoleDao;
import com.rimi.ctibet.web.service.IRoleService;
import com.rimi.ctibet.web.service.IUserInfoService;
@Service("roleService")
@Transactional
public class RoleServiceImpl implements IRoleService{

	@Resource
	private IRoleDao roleDao;
	@Resource
	private IRoleAccessDao roleAceesDao;
	@Resource
	private IUserInfoService userInfoService;
	@Override
	public void deleteRole(Role role) {
		role.setAvailable("0");
		roleDao.update(role);
		List<UserInfo> us = userInfoService.getUsByRole(role.getCode());
		for (UserInfo userInfo : us) {
			userInfo.setAvailable("0");
			userInfoService.updateUserInfo(userInfo);
		}
	}

	@Override
	public void saveRole(Role role) {
          roleDao.save(role);		
	}

	@Override
	public void updateRole(Role role) {
		roleDao.updateRole(role);
	}

	@Override
	public List<Role> getRoles() {
		return roleDao.findAllAvaliable();
	}

	@Override
	public void saveAccessForRole(RoleAccess roleAccess) {
			roleAceesDao.save(roleAccess);
	}

	@Override
	public Pager getAllRolesInfo(Pager pager,String keyWord) {
		return roleDao.getAllRolesInfo(pager,keyWord);
	}

	@Override
	public Role getRoleBuCode(String code) {
		if(StringUtils.isNotBlank(code))
			return roleDao.findByCodeAsHibernate(code);
		return null;
	}

	@Override
	public List<Map<String, Object>> getAllAccessByRoleCode(String roleCode) {
		 if(StringUtils.isNotBlank(roleCode))
			 return roleDao.getAllAccessByRoleCode( roleCode);
		return null;
	}

	@Override
	public boolean isRoleNameRepeat(String roleName,String code) {
		return  roleDao.isRoleNameRepeat(roleName,code);
	}
}

package com.rimi.ctibet.web.service.serviceimpl;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.vo.FatherToChild;
import com.rimi.ctibet.web.controller.vo.RoleVO;
import com.rimi.ctibet.web.controller.vo.UserVO;
import com.rimi.ctibet.web.dao.IAccessDao;
import com.rimi.ctibet.web.dao.IRoleDao;
import com.rimi.ctibet.web.dao.IUserInfoDao;
import com.rimi.ctibet.web.service.IUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {

	@Resource
	private IUserInfoDao userInfoDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	IAccessDao accessDao;

	@Override
	public List<String> accessList(String code) {
		return userInfoDao.accessList(code);
	}

	@Override
	public UserInfo getByCode(String code) {
		return userInfoDao.getByCode(code);
	}

	@Override
	public UserInfo checkLogin(String name, String pwd) throws Exception {
		return userInfoDao.checkLogin(name, pwd);
	}

	@Override
	public void deleteUserInfo(UserInfo userInfo) {
		userInfo.setAvailable("0");
		userInfoDao.updateAsHibernate(userInfo);
	}

	@Override
	public UserInfo getUserInfoByCode(String code) {
		return userInfoDao.findByCode(code);
	}

	@Override
	public void saveUserInfo(UserInfo userInfo) {
		userInfo.setCreateTime(new Date());
		userInfoDao.save(userInfo);

	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfo.setLastEditTime(new Date());
		userInfoDao.updateAsHibernate(userInfo);
	}

	@Override
	public List<Map<String,Object>> userInfoList() {
		return userInfoDao.getAllAdmin();
	}

	@Override
	public String changePwd(UserInfo userInfo, String newPwd) {
		if (userInfo.getPwd().equals(newPwd))
			return "新旧密码不能相同";
		userInfo.setPwd(newPwd);
		this.updateUserInfo(userInfo);
		return "修改成功";
	}

	@Override
	public UserVO getUserVo(String code) {
		// 根据用户表述 查用户基本信息
		// 根据用户角色标识 查角色基本信息
		// 根据本权限标识 查权限基本信息
		UserVO user = new UserVO();
		UserInfo userInfo = userInfoDao.findByCode(code);
		FatherToChild.fatherToChild(userInfo, user);
		RoleVO role = new RoleVO();
		
		if (roleDao.findByCode(user.getRoleCode())==null) {
			return user;
		}
		FatherToChild.fatherToChild(roleDao.findByCode(user.getRoleCode()),
				role);
		user.setRole(role);
		return user;
	}

	@Override
	public boolean isRepeat(String name) {
		boolean flag = false;
		List<UserInfo> us = userInfoDao.findAllAvaliable();
		for (UserInfo userInfo : us) {
			if(name.equals(userInfo.getName()) && "1".equals(userInfo.getAvailable())){
				flag = true;
			    return flag;
			}
		}
		return flag;
	}

	@Override
	public Pager searchUsers(Pager pager, String roleCode, String keyWord) {
		return userInfoDao.searchUsers(pager,roleCode,keyWord);
	}

	@Override
	public List<UserInfo> getUsByRole(String roleCode) {
        if(StringUtils.isBlank(roleCode))
		  return null;
        return userInfoDao.getUsByRole(roleCode);
	}

}

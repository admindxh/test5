package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.UserInfo;

public interface IUserInfoDao extends BaseDao<UserInfo>{

	//用户登录验证
	public UserInfo checkLogin(String name,String pwd)  throws Exception;
	//根据用户code获取用户
	public UserInfo getByCode(String code);
	//根据用户code获取权限集
	public List<String> accessList(String code);
	//获取管理员列表
	public List<Map<String,Object>> getAllAdmin();
	//========================================
	//高级查询
	public Pager searchUsers(Pager pager,String roleCode,String keyWord);
	public List<UserInfo> getUsByRole(String roleCode);
}

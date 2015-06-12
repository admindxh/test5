package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.vo.UserVO;

public interface IUserInfoService {
	
    public void saveUserInfo(UserInfo userInfo); 
	public void updateUserInfo(UserInfo userInfo);
	public void deleteUserInfo(UserInfo userInfo);
	public UserInfo getUserInfoByCode(String code);
	public List<Map<String,Object>> userInfoList();
	//用户登录验证
	public UserInfo checkLogin(String name,String pwd)  throws Exception;
	//根据用户code获取用户
	public UserInfo getByCode(String code);
	//根据用户code获取权限集
	public List<String> accessList(String code);
    //修改密码
	public String changePwd(UserInfo userInfo,String newPwd);
	//
	public UserVO getUserVo(String code);
	//========================
	//高级查询
	public Pager searchUsers(Pager pager,String roleCode,String keyWord);
	//账号名是否重复
	public boolean isRepeat(String name);
	public List<UserInfo> getUsByRole(String roleCode);
}

package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.domain.Access;

public interface IAccessService {
   
	public void saveAccess(Access access);
	public void updateAccess(Access access);
	public void deleteAccess(Access access);
	//通过code获取权限
	public Access getAccessByCode(String code);
	//权限列表
	public List<Access> accessList();
	//获取顶层权限
	public List<Access> getTopAccess();
	//获取二级权限
	public List<Access> getSendAccess(String pCode);
	//获取全部权限
	public Map<String,List<Access>> getAllAccess();
	
	
}

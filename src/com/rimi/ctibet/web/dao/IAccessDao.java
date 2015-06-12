package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.Access;

public interface IAccessDao extends BaseDao<Access>{
	//根据code获取权限信息
	public Access findByCode(String code);
	//删除权限
	public void deleteAccess(Access access);
    //权限列表
	public List<Access> accessList();
	//获取顶级权限
	public List<Access> getTopAccess();
	//获取二级全系
	public List<Access> getSendAccess(String pCode);
	//获取全部权限
	public Map<String,List<Access>> getAllAccess();
}

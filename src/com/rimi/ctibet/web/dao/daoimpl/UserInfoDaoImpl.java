package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.dao.IRoleDao;
import com.rimi.ctibet.web.dao.IUserInfoDao;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements IUserInfoDao{

	@Resource
	private IRoleDao roleDao;
	@Override
	public List<String> accessList(String code) {
		UserInfo userInfo = this.getByCode(code);
		String roleCode = userInfo.getRoleCode();
		List<String> access = roleDao.getAccessByRoleCode(roleCode);
		return access;
	}
	@Override
	public UserInfo getByCode(final String code) {
		return getHibernateTemplate().execute(new HibernateCallback<UserInfo>() {
            public UserInfo doInHibernate(Session arg0) throws HibernateException, SQLException {
                Query query= arg0.createQuery("select a  from UserInfo a   where  a.code=:code");
                query.setParameter("code", code);
                return (UserInfo) query.uniqueResult();
            }
        });
	}
	@Override
	public UserInfo checkLogin(String name, String pwd) throws Exception {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from user_info where name = ? and pwd = ? and avaliable <> 0";
		list.add(name);
		list.add(pwd);
		List<Map<String,Object>> userInfo = new ArrayList<Map<String,Object>>();
		userInfo = findByJDBCSql(sql, list);
		if(userInfo.size()==0){
		   return null;}
	    UserInfo user = new UserInfo();
	    user.setName(userInfo.get(0).get("name").toString());
	    user.setPwd(userInfo.get(0).get("pwd").toString());
	    user.setCode(userInfo.get(0).get("code").toString());
	    user.setRoleCode(userInfo.get(0).get("rolecode").toString());
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date createTime = sdf.parse(userInfo.get(0).get("createtime").toString());
	    Date lastEditTime = sdf.parse(userInfo.get(0).get("lastedittime").toString());
	    user.setCreateTime(createTime);
	    user.setLastEditTime(lastEditTime);
	    return user;
	}
//=======================================最终版本=====================================
	@Override
	public List<Map<String, Object>> getAllAdmin() {
		String sql = "SELECT ui.*,r.`name` AS rolename " +
				"FROM user_info ui, role r  " +
				"WHERE ui.roleCode = r.`code` AND ui.avaliable = '1' ";
		return findByJDBCSql(sql,null);
	}
	@Override
	public Pager searchUsers(Pager pager, String roleCode, String keyWord) {
		String sql = "select  ui.`code`,ui.name,ui.pwd,ui.createTime,ui.id,r.`rolename`  " +
		"FROM user_info ui, role r  " +
		"WHERE ui.roleCode = r.`code` AND ui.avaliable = '1' ";
    	List<Object> params = new ArrayList<Object>();
    	if(StringUtils.isNotBlank(roleCode)){
    	   sql += " AND r.`code` = ? ";
    	   params.add(roleCode);
    	}
    	if(StringUtils.isNotBlank(keyWord)){
    	  sql += " AND ui.`name` LIKE ? ";
    	  params.add("%"+keyWord+"%");
    	}
    	sql += " ORDER BY ui.createTime DESC ";
   		return findPagerBySQL(sql, params, pager);
	}
	public List<UserInfo> getUsByRole(String roleCode){
		String sql = "SELECT ui.`code` FROM user_info ui WHERE ui.roleCode = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(roleCode);
		List<Map<String,Object>> result = findByJDBCSql(sql, params);
		List<UserInfo> us = new ArrayList<UserInfo>();
		if(result!=null&&result.size()>0)
		for (Map<String, Object> map : result) {
			UserInfo ui = new UserInfo();
			ui = this.findByCode(map.get("code").toString());
			us.add(ui);
		}
		return us;
	}

}

package com.rimi.ctibet.web.dao.daoimpl;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.web.dao.IRoleDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao{

	@Override
	public Role findByCode(final String code){
		return getHibernateTemplate().execute(new HibernateCallback<Role>() {
            public Role doInHibernate(Session arg0) throws HibernateException, SQLException {
                Query query= arg0.createQuery("select a  from Role a   where  a.code=:code");
                query.setParameter("code", code);
                return (Role) query.uniqueResult();
            }
		});
	}
	
	@Override
	public List<String> getAccessByRoleCode(String code) {
		Role role = this.findByCode(code);
		String[] access = role.getAccess().split(",");
		return Arrays.asList(access);
	}

	@Override
	public void saveAccess(Access access, Role role) {
		String accessString = role.getAccess();
		accessString +=","+access.getCode();
		role.setAccess(accessString);
		
	}
    //==================================================================================================
	@Override
	public void deleteRole(Role role) {
		final Role role2 = role;
		this.delete(role);
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete from RoleAccess  where roleCode=:code").setParameter("code", role2.getCode()).executeUpdate();
            }
        });
	}

	@Override
	public void updateRole(Role role) {
		final Role role2 = role;
		this.updateAsHibernate(role);
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete from RoleAccess where rolecode=:code").setParameter("code", role2.getCode()).executeUpdate();
            }
        });
	}

	@Override
	public Pager getAllRolesInfo(Pager pager,String keyWord) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT r.* ,group_concat(ra.accessName) AS resouce " +
				"FROM role r,role_acces ra " +
				"WHERE r.`code` = ra.rolecode AND r.avaliable = 1 ";
		if(StringUtils.isNotBlank(keyWord)){
			params.add("%"+keyWord+"%");
			sql += "AND r.rolename LIKE ? ";
		}
		sql += " GROUP BY r.`code`";
		return findPagerBySQL(sql, params, pager);
	}

	@Override
	public List<Map<String, Object>> getAllAccessByRoleCode(String roleCode) {
		String sql = "SELECT CONCAT(ra.accessurl,'_' ,ra.accessName) AS access " +
				"FROM role_acces ra " +
				"WHERE ra.rolecode = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(roleCode);
		return findByJDBCSql(sql, params);
	}

	@Override
	public boolean isRoleNameRepeat(String roleName,String code) {
		List<Object> params = new ArrayList<Object>();
		params.add(roleName);
		String sql = "SELECT COUNT(r.`code`) AS num FROM role r WHERE r.avaliable = 1 AND r.rolename = ? ";
        if (StringUtils.isNotBlank(code)) {
            sql += "and r.`code` != ?";
            params.add(code);
        }
		List<Map<String,Object>> result = findByJDBCSql(sql, params);
		Map<String,Object> map = result.get(0);
		int num = Integer.valueOf(map.get("num").toString());
		//System.out.println(num);
		if(num>0) return true;
		else return false;
	}
}

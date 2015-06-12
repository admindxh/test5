package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.web.dao.IAccessDao;
@Repository("accessDao")
public class AccessDaoImpl extends BaseDaoImpl<Access> implements IAccessDao{

	@Override
	public Access findByCode(String code) {
		//Query query= getSession().createQuery("select a  from Access a   where  a.code=:code");
		List ls= getHibernateTemplate().find("select a  from Access a   where  a.code=?",code);
		return (Access)ls.get(0);
		//return (Access) query.uniqueResult();
	}
	@Override
	public void deleteAccess(Access access) {
		super.delete(access);
	}
	@Override
	public List<Access> accessList() {
		return super.findAll();
	}
	@Override
	public List<Access> getTopAccess() {
		String hql = "from Access where pCode = null";  
		List<Access> list = getSession().createQuery(hql).list();	  
        return list;  
	}
	@Override
	public List<Access> getSendAccess(String pCode) {
		String hql = "from Access where pCode = ? ";  
		List<Access> list = getSession().createQuery(hql).setString(0, pCode).list();	  
        return list; 
	}
	@Override
	public Map<String,List<Access>> getAllAccess() {
		Map<String,List<Access>> map =new HashMap<String,List<Access>>();
		List<Access> top = this.getTopAccess();
        for (Access access : top) {
		    List<Access> send = new ArrayList<Access>();
		    send.add(access);
		    send.addAll(this.getSendAccess(access.getCode()));
			map.put(access.getName(), send);
		}
		return map;
	}
}

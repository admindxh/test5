package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.GroupTravelView;
import com.rimi.ctibet.web.dao.IGroupTravelViewDao;
@Repository("groupTravelViewDao")
public class GroupTravelViewDaoImpl extends BaseDaoImpl<GroupTravelView> implements IGroupTravelViewDao{
	
	@Override
	public void deleteByGroupTravelCode(String groupTravelCode){
		final String code =  groupTravelCode;
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete from "+domainClass.getName() +"  where groupTravelCode=:code").setParameter("code", code).executeUpdate();
            }
        });
	}
}

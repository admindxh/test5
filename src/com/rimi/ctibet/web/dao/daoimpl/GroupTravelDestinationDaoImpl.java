package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.GroupTravelDestination;
import com.rimi.ctibet.web.dao.IGroupTravelDeatinationDao;
@Repository("groupTravelDestinationDao")
public class GroupTravelDestinationDaoImpl extends BaseDaoImpl<GroupTravelDestination> implements IGroupTravelDeatinationDao{
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

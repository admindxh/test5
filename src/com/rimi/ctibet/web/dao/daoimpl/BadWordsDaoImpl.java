package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.BadWords;
import com.rimi.ctibet.web.dao.BadWordsDao;
@Repository("badWordsDao")
public class BadWordsDaoImpl extends BaseDaoImpl<BadWords> implements BadWordsDao{

	@Override
	public List<BadWords> findByPro(final String pro,final Object value) {
		// TODO Auto-generated method stub
		 List<BadWords> list=getHibernateTemplate().execute(new HibernateCallback<List<BadWords>>(){
            @Override
			public List<BadWords> doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				/*String queryString = "from BadWords"
				+ " as model where model." + pro + "='"+value+"'";*/
            	String queryString = "from BadWords as model where model." + pro + "=?";
			   //Query query= session.createQuery(queryString);
            	Query query= session.createQuery(queryString).setParameter(0, value);
				return query.list();
			}
			
		});
		return list;
	}
}

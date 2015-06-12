package com.rimi.ctibet.web.dao.daoimpl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.VeDate;
import com.rimi.ctibet.domain.PageView;
import com.rimi.ctibet.web.dao.IPageViewDao;

/**
 * 访问量统计实现类
 * @author dengxh
 *
 */
@Repository(value="pageViewDao")
public class PageViewDaoImpl extends BaseDaoImpl<PageView> implements IPageViewDao {


	@Override
	public Integer getMemCountByTime(final Date start, final Date end) {
		// TODO Auto-generated method stub
		Integer  pvCount  = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				      String sql  = "select count(*) from pageview where 1=1 " ;
				    
				      
				      if(start!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(pageview.viewtime,'%Y-%m-%d')>='"+VeDate.toDateString(start)+"'";
				      }
				      if(end!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(pageview.viewtime,'%Y-%m-%d')<='"+VeDate.toDateString(end)+"'";
				      }
				      Query query  = session.createSQLQuery(sql);
				      
				      BigInteger  bigInteger  = (BigInteger) query.uniqueResult();
						return   Integer.valueOf(bigInteger.toString());
			}
		});
		return pvCount; 
	 }


	

}

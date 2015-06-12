package com.rimi.ctibet.web.dao.daoimpl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.VeDate;
import com.rimi.ctibet.domain.LoginLog;
import com.rimi.ctibet.web.dao.ILoginLogDao;
@Repository("loginLogDao")
public class LoginLogDaoImpl extends BaseDaoImpl<LoginLog> implements ILoginLogDao{

	@SuppressWarnings("unchecked")
	@Override
	public Integer getActUserNum(final Date start, final Date end) {
		
		

	   // TODO Auto-generated method stub
			Integer  pvCount  = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				List<Object> params=new ArrayList<Object>();
				@Override
				public Integer doInHibernate(Session session) throws HibernateException,
						SQLException {
					// TODO Auto-generated method stub
					String sql="SELECT DISTINCT  count(memberCode) FROM login_log WHERE 1=1 ";
					 if(start!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(logDate,'%Y-%m-%d')>='"+VeDate.toDateString(start)+"'";
				      }
				      if(end!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(logDate,'%Y-%m-%d')<='"+VeDate.toDateString(end)+"'";
				      }
				      sql  = sql  +" ";
					      Query query  = session.createSQLQuery(sql);
					      
					      BigInteger  bigInteger  = (BigInteger) query.uniqueResult();
						return   bigInteger!=null?Integer.valueOf(bigInteger.toString()):0;
				}
			});
			return pvCount; 
	}
   
}

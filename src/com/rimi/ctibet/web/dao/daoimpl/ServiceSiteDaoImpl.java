package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ServiceSite;
import com.rimi.ctibet.web.dao.IServiceSiteDao;

@Repository
public class ServiceSiteDaoImpl  extends BaseDaoImpl<ServiceSite> implements  IServiceSiteDao{
	public Pager getPagerSite(String siteName,String serviceName,String ridelineId,Pager pager){
		String sql = " SELECT  st.*,rl.`name` as name from servicesite  st  LEFT join  ride_line rl ON rl.code = st.ridelineId and  rl.avaliable=1  where st.avaliable=1 " ;
		List<String> params  = new  ArrayList<String>();
		if (StringUtils.isNotBlank(siteName)) {
		      sql += " and ( st.sitename like  ? or  st.servicename like  ? or  st.code = ? )";
		      params.add("%"+siteName+"%");
		      params.add("%"+siteName+"%");
		      params.add(siteName);
		}
		if (StringUtils.isNotBlank(serviceName)) {
		      //sql += " and st.servicename like  ?";
		      //params.add("%"+serviceName+"%");
		}
		if (StringUtils.isNotBlank(ridelineId)) {
		      sql += " and st.ridelineId   =? ";
		      params.add(ridelineId);
		}
		sql +=" order by st.orderSite ";
		
	  return  	super.findPagerBySQL(sql, params, pager);
	}

    @Override
    public void deleteByLineCode(final String code) {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session s) throws HibernateException, SQLException {
                //System.out.println("======================================="+code);
                return s.createSQLQuery(" update servicesite set avaliable = 0 where ridelineId=:code ").setParameter("code", code).executeUpdate();
            }
        });
    }
}

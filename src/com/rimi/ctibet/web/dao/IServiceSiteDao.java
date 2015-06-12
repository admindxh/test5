package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ServiceSite;

public interface IServiceSiteDao  extends BaseDao<ServiceSite>{
	  
	public Pager getPagerSite(String siteName,String serviceName,String ridelineId,Pager pager);
	
	public void deleteByLineCode(String code);
	
}

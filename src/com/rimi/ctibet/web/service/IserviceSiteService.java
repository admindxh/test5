package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ServiceSite;

public interface IserviceSiteService  extends BaseService<ServiceSite>{
	public Pager getPagerSite(String siteName,String serviceName,String ridelineId,Pager pager);
	public void deleteByLineCode(String code);
}

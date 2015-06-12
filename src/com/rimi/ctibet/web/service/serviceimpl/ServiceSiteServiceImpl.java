package com.rimi.ctibet.web.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ServiceSite;
import com.rimi.ctibet.web.dao.IServiceSiteDao;
import com.rimi.ctibet.web.service.IserviceSiteService;

@Transactional
@Service
public class ServiceSiteServiceImpl  extends BaseServiceImpl<ServiceSite> implements IserviceSiteService{
   
	@Autowired
	private IServiceSiteDao serviceSiteDao;

	public IServiceSiteDao getServiceSiteDao() {
		return serviceSiteDao;
	}

	public void setServiceSiteDao(IServiceSiteDao serviceSiteDao) {
		this.serviceSiteDao = serviceSiteDao;
	}

	
	public Pager getPagerSite(String siteName,String serviceName,String ridelineId,Pager pager){
		
		 return serviceSiteDao.getPagerSite(siteName, serviceName, ridelineId, pager);
		
	}

    @Override
    public void deleteByLineCode(String code) {
        serviceSiteDao.deleteByLineCode(code);
    }
	
	
	
	
	
}

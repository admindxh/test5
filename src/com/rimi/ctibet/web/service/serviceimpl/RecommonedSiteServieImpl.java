package com.rimi.ctibet.web.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.RecommonedSite;
import com.rimi.ctibet.web.dao.IRecommonedSiteDao;
import com.rimi.ctibet.web.service.IRecommonedSiteService;

@Service
@Transactional
public class RecommonedSiteServieImpl  extends BaseServiceImpl<RecommonedSite> implements IRecommonedSiteService{
	
    @Autowired
	private 	IRecommonedSiteDao recommonedSiteDao ;

	public IRecommonedSiteDao getRecommonedSiteDao() {
		return recommonedSiteDao;
	}

	public void setRecommonedSiteDao(IRecommonedSiteDao recommonedSiteDao) {
		this.recommonedSiteDao = recommonedSiteDao;
	}
    
    
    
    
}

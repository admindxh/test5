package com.rimi.ctibet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.web.service.IRecommonedSiteService;

@RequestMapping("web/recomoned")
public class ReommonedServiceSiteController  extends BaseController{
	
	@Autowired
	private IRecommonedSiteService recommonedSiteService;

	public IRecommonedSiteService getRecommonedSiteService() {
		return recommonedSiteService;
	}

	public void setRecommonedSiteService(
			IRecommonedSiteService recommonedSiteService) {
		this.recommonedSiteService = recommonedSiteService;
	}

	
	
}

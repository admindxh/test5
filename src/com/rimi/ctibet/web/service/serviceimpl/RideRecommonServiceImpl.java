package com.rimi.ctibet.web.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.RideRecommon;
import com.rimi.ctibet.web.dao.IRideRecommonDao;
import com.rimi.ctibet.web.service.IRideCommonService;

@Transactional
@Service
public class RideRecommonServiceImpl extends BaseServiceImpl<RideRecommon> implements IRideCommonService {
		
	@Autowired
     private 	IRideRecommonDao rideRecommonDao;

	public IRideRecommonDao getRideRecommonDao() {
		return rideRecommonDao;
	}

	public void setRideRecommonDao(IRideRecommonDao rideRecommonDao) {
		this.rideRecommonDao = rideRecommonDao;
	}
	
}

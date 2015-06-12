package com.rimi.ctibet.web.service.serviceimpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.web.dao.IRideLineDao;
import com.rimi.ctibet.web.dao.IServiceSiteDao;
import com.rimi.ctibet.web.service.IRideLineService;

@Service("RideLineService")
public class RideLineServiceImpl extends BaseServiceImpl<RideLine> implements IRideLineService {

    @Resource IRideLineDao rideLineDao;
    
    @Autowired
    private IServiceSiteDao serviceSiteDao;
    
    @Override
    public void deleteRideLines(String[] codes) {
        if(codes!=null && codes.length > 0){
            for (String code : codes) {
                rideLineDao.deleteLogicalByCode(code);
                serviceSiteDao.deleteByLineCode(code);
            }
        }
    }

    @Override
    public Pager searchRideLineByName(Pager pager, String name) {
        return rideLineDao.searchRideLineByName(pager, name);
    }
    
    /********************************************
     * Setter Getter
     ********************************************/
    
    public IRideLineDao getRideLineDao() {
        return rideLineDao;
    }
    
    public void setRideLineDao(IRideLineDao rideLinedao) {
        this.rideLineDao = rideLinedao;
    }

    public IServiceSiteDao getServiceSiteDao() {
        return serviceSiteDao;
    }

    public void setServiceSiteDao(IServiceSiteDao serviceSiteDao) {
        this.serviceSiteDao = serviceSiteDao;
    }



}

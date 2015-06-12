package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.web.dao.IMobileCodeDao;
import com.rimi.ctibet.web.service.IMobileCodeService;
@Service("mobileCodeService")
@Transactional
public class MobileCodeServiceImpl extends BaseServiceImpl<MobileCode> implements IMobileCodeService{
   @Resource
   private IMobileCodeDao mobileCodeDao;

public IMobileCodeDao getMobileCodeDao() {
	return mobileCodeDao;
}

public void setMobileCodeDao(IMobileCodeDao mobileCodeDao) {
	this.mobileCodeDao = mobileCodeDao;
}

@Override
public List<MobileCode> findValicode(String mobile) {
	// TODO Auto-generated method stub
	return mobileCodeDao.findByMobile(mobile);
}

@Override
public List<MobileCode> findValicode(String mobile, String code) {
	// TODO Auto-generated method stub
	return mobileCodeDao.findByMobileAndValidate(mobile, code);
}
   
}

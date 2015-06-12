package com.rimi.ctibet.web.service.serviceimpl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.LoginLog;
import com.rimi.ctibet.web.dao.ILoginLogDao;
import com.rimi.ctibet.web.service.ILoginLogService;
@Service("loginLogService")
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog> implements ILoginLogService{
    
	@Resource
	private ILoginLogDao loginLogDao;
	public ILoginLogDao getLoginLogDao() {
		return loginLogDao;
	}
	public void setLoginLogDao(ILoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}
	@Override
	public Integer getActUserNum(Date start, Date end) {
		// TODO Auto-generated method stub
		return loginLogDao.getActUserNum(start, end);
	}

}

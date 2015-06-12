package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Surguestion;
import com.rimi.ctibet.web.dao.ISurguestionDao;
import com.rimi.ctibet.web.service.ISurguestionService;
@Service("surguestionService")
public class SurguestionServiceImpl extends BaseServiceImpl<Surguestion> implements ISurguestionService{
   @Resource
   private ISurguestionDao surguestionDao;

public ISurguestionDao getSurguestionDao() {
	return surguestionDao;
}

public void setSurguestionDao(ISurguestionDao surguestionDao) {
	this.surguestionDao = surguestionDao;
}

@SuppressWarnings("unchecked")
@Override
public Pager findListBySql(List param, Pager pager) {
	// TODO Auto-generated method stub
	String sql="SELECT * FROM `surguestion` where avaliable='1' order by createDate desc";
	
	return surguestionDao.findListPagerBySql(Surguestion.class, pager, sql, param);
}
}

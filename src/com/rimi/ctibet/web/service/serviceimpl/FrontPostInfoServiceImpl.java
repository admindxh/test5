package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.dao.IFrontPostInfoDao;
import com.rimi.ctibet.web.service.IFrontPostInfoService;

/**
 * 
 * @author dengxh
 *
 */
@Service("frontPostInfoService")
@Transactional
public class FrontPostInfoServiceImpl  extends BaseServiceImpl<Content> implements IFrontPostInfoService{

	@Resource
	IFrontPostInfoDao frontPostInfoDao;
	
	@Override
	public Content findByCodeContentType(String code, String contentType) {
		// TODO Auto-generated method stub
		return  frontPostInfoDao.findByCodeContentType(code, contentType);
	}

	@Override
	public  List<Map<String,Object>> findToInfo(String code, String contentType) {
		// TODO Auto-generated method stub
		return frontPostInfoDao.findToInfo(code, contentType);
	}

	@Override
	public  Pager findbyFatherCode(String code,
			String contentType,Pager  pager) {
		// TODO Auto-generated method stub
		return frontPostInfoDao.findbyFatherCode(code, contentType,pager);
	}
	@Override
	public Pager findbyMemberCode(String createusercode, Pager pager) {
		// TODO Auto-generated method stub
		return this.frontPostInfoDao.findbyMemberCode(createusercode, pager);
	}

	public IFrontPostInfoDao getFrontPostInfoDao() {
		return frontPostInfoDao;
	}

	public void setFrontPostInfoDao(IFrontPostInfoDao frontPostInfoDao) {
		this.frontPostInfoDao = frontPostInfoDao;
	}

	
}

package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.MerchantAndView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.IMerchantAndViewDao;
import com.rimi.ctibet.web.service.IMerchantAndViewService;

@Repository("merchantAndviewService")
@Transactional
public class MerchantAndViewServiceImpl  extends BaseServiceImpl<MerchantAndView> implements IMerchantAndViewService {
	
	@Resource
	private IMerchantAndViewDao merchantAndViewDao;

	public IMerchantAndViewDao getMerchantAndViewDao() {
		return merchantAndViewDao;
	}

	public void setMerchantAndViewDao(IMerchantAndViewDao merchantAndViewDao) {
		this.merchantAndViewDao = merchantAndViewDao;
	}
	
	
	public List<View> getViewsByMCode(String code){
		
	   return 	merchantAndViewDao.getViewsByMCode(code);
		
	}
	public void deleteByMerchatCode(final String merchantCode){
		
		merchantAndViewDao.deleteByMerchatCode(merchantCode);
	}
	
}

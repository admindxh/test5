package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.MerchantAndView;
import com.rimi.ctibet.domain.View;

public interface IMerchantAndViewService extends   BaseService<MerchantAndView>  {
	public List<View> getViewsByMCode(String code);
	public void deleteByMerchatCode(final String merchantCode);
}

package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.DesAndViewStatis;
import com.rimi.ctibet.web.controller.vo.DesAndViewStatisVo;

public interface IDesAndViewService  extends  BaseService<DesAndViewStatis>  {
	public DesAndViewStatisVo getDesViewVo(String start, String end,
			String desCode, String viewCode) ;
}

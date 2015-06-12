package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.DesAndViewStatis;
import com.rimi.ctibet.web.controller.vo.DesAndViewStatisVo;

public interface IDesAndViewStatisDao  extends BaseDao<DesAndViewStatis>{
		
	public DesAndViewStatisVo  getDesViewVo(String start,String end,String desCode,String viewCode);
		
		
		
	
}

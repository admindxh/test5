package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.SysMessage;

public interface ISysMessageService extends BaseService<SysMessage>{
	public Pager findWithPagerByMap(String hql, Map<String, Object> Param, Pager pager);
	public List<SysMessage> findListBysql(String sql, List<Object> params);
	public Pager findByJdbcPager(String sql,List params,Pager pager);
	//获取未读数量
	public int unReadCount(String memberCode);
}

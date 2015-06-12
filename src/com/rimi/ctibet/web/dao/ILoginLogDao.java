package com.rimi.ctibet.web.dao;

import java.util.Date;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.LoginLog;

public interface ILoginLogDao extends BaseDao<LoginLog>{
	 //根据时间区间查询活跃数
	public Integer getActUserNum(Date start,Date end);
}

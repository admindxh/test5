package com.rimi.ctibet.web.service;

import java.util.Date;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.LoginLog;

public interface ILoginLogService extends BaseService<LoginLog>{
    //根据时间区间查询活跃数
	public Integer getActUserNum(Date start,Date end);
}

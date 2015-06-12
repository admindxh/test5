package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Surguestion;

public interface ISurguestionService extends BaseService<Surguestion>{
    @SuppressWarnings("unchecked")
	public Pager findListBySql(List param,Pager pager);
}

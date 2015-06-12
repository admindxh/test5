package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Equipment;

public interface IEquimentService  extends BaseService<Equipment>{
	public Pager getEquiList(String name,String proType,Pager pager);
}

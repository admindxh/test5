package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.MemberMobile;

public interface IMemberMobileService extends BaseService<MemberMobile>{
    //校验电话号码
	public List<MemberMobile> checkMobile(String mobile);
		
}

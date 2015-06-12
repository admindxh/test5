package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.UserSysMessage;

public interface IUserSysMessageService extends BaseService<UserSysMessage>{
   //判断信息是否已读
	public boolean isReadOrNot(String memberCode,String msgCode,String msgType);
}

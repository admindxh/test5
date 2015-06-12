package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.UserSysMessage;
import com.rimi.ctibet.web.dao.IUserSysMessageDao;
import com.rimi.ctibet.web.service.IUserSysMessageService;
@Service("userSysMessageService")
@Transactional
public class UserSysMessageServiceImpl extends BaseServiceImpl<UserSysMessage> implements IUserSysMessageService{
   @Resource
   private IUserSysMessageDao userSysMessageDao;

public IUserSysMessageDao getUserSysMessageDao() {
	return userSysMessageDao;
}

public void setUserSysMessageDao(IUserSysMessageDao userSysMessageDao) {
	this.userSysMessageDao = userSysMessageDao;
}

@SuppressWarnings("unchecked")
@Override
public boolean isReadOrNot(String memberCode, String msgCode, String msgType) {
	// TODO Auto-generated method stub
	String sql="SELECT * FROM `user_sysmessage` WHERE memberCode=? AND msgCode=? AND type=?";
	List params=new ArrayList();
	 params.add(memberCode);
	 params.add(msgCode);
	 params.add(msgType);
	List<UserSysMessage> list=userSysMessageDao.findListBySql(UserSysMessage.class,sql, params);
	return list.size()>0;
}
   
}

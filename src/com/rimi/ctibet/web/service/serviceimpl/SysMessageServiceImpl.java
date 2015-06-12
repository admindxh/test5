package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.domain.UserSysMessage;
import com.rimi.ctibet.web.dao.ISysMessageDao;
import com.rimi.ctibet.web.dao.IUserSysMessageDao;
import com.rimi.ctibet.web.service.ISysMessageService;
@Service("sysMessageService")
@Transactional
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage> implements ISysMessageService{
    @Resource
	private ISysMessageDao sysMessageDao;
    @Resource
    private IUserSysMessageDao userSysMessageDao;
public ISysMessageDao getSysMessageDao() {
	return sysMessageDao;
}

public void setSysMessageDao(ISysMessageDao sysMessageDao) {
	this.sysMessageDao = sysMessageDao;
}

@Override
public Pager findWithPagerByMap(String hql, Map<String, Object> Param,
		Pager pager) {
	// TODO Auto-generated method stub
	return sysMessageDao.findWithPagerByMap(hql, Param, pager);
}

@Override
public List<SysMessage> findListBysql(String sql, List<Object> params) {
	// TODO Auto-generated method stub
	return sysMessageDao.findListBySql(SysMessage.class, sql, params);
}

@Override
public Pager findByJdbcPager(String sql, List params, Pager pager) {
	// TODO Auto-generated method stub
	return sysMessageDao.findListPagerBySql(SysMessage.class, pager, sql, params);
}
@SuppressWarnings("unchecked")
@Override
public int unReadCount(String memberCode) {
	// TODO Auto-generated method stub
	String sql="select * FROM sysmessage WHERE avaliable='1' AND (msgTo=? or msgTo='all') order by createDate desc";
	List param=new ArrayList();
	 param.add(memberCode);
	List<SysMessage> list=sysMessageDao.findListBySql(SysMessage.class, sql, param);
	List<SysMessage> cnt=new ArrayList<SysMessage>();
	for(SysMessage sm:list){
		if(sm.getCode()!=null&&sm.getType()!=null){
			if(isReadOrNot(memberCode, sm.getCode(), sm.getType())){
               cnt.add(sm);  	
            }
		}
	}
	list.removeAll(cnt);
	return list.size();
}  
@SuppressWarnings("unchecked")
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

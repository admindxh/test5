package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.web.dao.IMemberMobileDao;
import com.rimi.ctibet.web.service.IMemberMobileService;
@Service("memberMobileService")
@Transactional
public class MemberMobileServiceImpl extends BaseServiceImpl<MemberMobile> implements IMemberMobileService{
    @Resource
	private IMemberMobileDao memberMobileDao;

public IMemberMobileDao getMemberMobileDao() {
	return memberMobileDao;
}

public void setMemberMobileDao(IMemberMobileDao memberMobileDao) {
	this.memberMobileDao = memberMobileDao;
}

	@Override
	public List<MemberMobile> checkMobile(String mobile) {
		List<MemberMobile> list=memberMobileDao.findByProperty("mobile", mobile);
		return list;
	}
}

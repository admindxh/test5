package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.web.dao.IMemberEmailDao;
import com.rimi.ctibet.web.service.IMemberEmailService;
@Transactional
@Service("memberEmail")
public class MemberEmailServiceImpl extends BaseServiceImpl<MemberEmail> implements IMemberEmailService{
    @Resource
    private IMemberEmailDao memberEmailDao;
	public IMemberEmailDao getMemberEmailDao() {
		return memberEmailDao;
	}
	public void setMemberEmailDao(IMemberEmailDao memberEmailDao) {
		this.memberEmailDao = memberEmailDao;
	}
	@Override
	public List<MemberEmail> checkEmail(String email) {
		// TODO Auto-generated method stub
		List<MemberEmail> list=memberEmailDao.findByProperty("email", email);
		return list;
	}
    
}

package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.MemberEmail;

public interface IMemberEmailService extends BaseService<MemberEmail>{
   //检查邮件
	public List<MemberEmail> checkEmail(String email);
}

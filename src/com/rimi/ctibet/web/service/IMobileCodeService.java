package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.MobileCode;

public interface IMobileCodeService extends BaseService<MobileCode>{
   public List<MobileCode> findValicode(String mobile);
   public List<MobileCode> findValicode(String mobile,String code);
}

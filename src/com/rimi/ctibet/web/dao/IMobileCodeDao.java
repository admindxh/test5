package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.MobileCode;

public interface IMobileCodeDao extends BaseDao<MobileCode> {
        
	
	public List<MobileCode> findByMobileAndValidate(String mobile,String validate);

	public List<MobileCode> findByMobile(String mobile);
}

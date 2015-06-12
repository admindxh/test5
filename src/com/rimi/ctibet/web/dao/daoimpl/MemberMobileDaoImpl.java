package com.rimi.ctibet.web.dao.daoimpl;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.web.dao.IMemberMobileDao;
@Repository("memberMobileDao")
public class MemberMobileDaoImpl extends BaseDaoImpl<MemberMobile> implements
		IMemberMobileDao {

}

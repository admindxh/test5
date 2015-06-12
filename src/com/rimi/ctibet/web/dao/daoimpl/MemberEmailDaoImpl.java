package com.rimi.ctibet.web.dao.daoimpl;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.web.dao.IMemberEmailDao;
@Repository("memberEmailDao")
public class MemberEmailDaoImpl extends BaseDaoImpl<MemberEmail> implements
		IMemberEmailDao {

}

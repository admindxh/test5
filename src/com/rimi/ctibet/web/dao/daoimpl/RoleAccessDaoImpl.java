package com.rimi.ctibet.web.dao.daoimpl;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.RoleAccess;
import com.rimi.ctibet.web.dao.IRoleAccessDao;
@Repository("roleAccessDao")
public class RoleAccessDaoImpl extends BaseDaoImpl<RoleAccess> implements IRoleAccessDao{

}

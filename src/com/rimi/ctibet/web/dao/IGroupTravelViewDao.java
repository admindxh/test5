package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.GroupTravelView;

public interface IGroupTravelViewDao extends BaseDao<GroupTravelView>{
	
	public void deleteByGroupTravelCode(String groupTravelCode);

}

package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.GroupTravelDestination;

public interface IGroupTravelDeatinationDao extends BaseDao<GroupTravelDestination> {
	public void deleteByGroupTravelCode(String groupTravelCode);
}

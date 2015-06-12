package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.EnrollForm;

public interface IEnrollFormDao extends BaseDao<EnrollForm> {

	/**
	 * 通过活动code获取报名表单
	 * @param code
	 * @return
	 */
	public List<EnrollForm> getEnrollFormByActivityCode(String activityCode);
	
}

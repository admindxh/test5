package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.EnrollNotice;

public interface IEnrollNoticeDao extends BaseDao<EnrollNotice> {

	/**
	 * 通过活动code获取报名须知
	 * @param activityCode
	 * @return
	 */
	public EnrollNotice getEnrollNoticesByActivityCode(String activityCode);
	
}

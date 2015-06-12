package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.EnrollNotice;

public interface IEnrollNoticeService extends BaseService<EnrollNotice> {

	/**
	 * 通过活动code获取报名须知
	 * @param activityCode
	 * @return
	 */
	public EnrollNotice getEnrollNoticesByActivityCode(String activityCode);
	
}

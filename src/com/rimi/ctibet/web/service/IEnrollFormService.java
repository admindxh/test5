package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.EnrollForm;

public interface IEnrollFormService extends BaseService<EnrollForm> {

	/**
	 * 通过活动code获取报名表单
	 * @param code
	 * @return
	 */
	public List<EnrollForm> getEnrollFormByActivityCode(String activityCode);
	
}

package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.VoteOption;

public interface IVoteOptionService extends BaseService<VoteOption> {
	
	/**
	 * 通过活动code获取选项
	 * @param activityCode
	 * @return
	 */
	public List<VoteOption> getVoteOptionsByActivityCode(String activityCode);
	
	/**
	 * 投票
	 */
	public void updateVoteOptionCountByCode(String code);
	
	/**
	 * 通过选项code 更新伪投票数
	 * @param colums
	 * @param value
	 */
	public void updateFakeCountsByCode(String code, int fakeCounts);
	
}

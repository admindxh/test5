package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.VoteOption;

public interface IVoteOptionDao extends BaseDao<VoteOption> {

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
	
	/**
	 * 通过选项code获取选项
	 */
	public VoteOption getVoteOptionByCode(String code);
	
}

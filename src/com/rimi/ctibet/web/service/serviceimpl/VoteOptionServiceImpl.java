package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.VoteOption;
import com.rimi.ctibet.web.dao.IVoteOptionDao;
import com.rimi.ctibet.web.service.IVoteOptionService;

@Service("voteOptionService")
@Transactional
public class VoteOptionServiceImpl extends BaseServiceImpl<VoteOption> implements IVoteOptionService {

	@Resource IVoteOptionDao voteOptionDao;

	/**
	 * 通过活动code获取选项
	 * @param activityCode
	 * @return
	 */
	public List<VoteOption> getVoteOptionsByActivityCode(String activityCode){
		return voteOptionDao.getVoteOptionsByActivityCode(activityCode);
	}
	
	/**
	 * 投票
	 */
	public void updateVoteOptionCountByCode(String code){
		voteOptionDao.updateVoteOptionCountByCode(code);
	}
	
	/**
	 * 通过选项code 更新伪投票数
	 * @param colums
	 * @param value
	 */
	public void updateFakeCountsByCode(String code, int fakeCounts){
		voteOptionDao.updateFakeCountsByCode(code, fakeCounts);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IVoteOptionDao getVoteOptionDao() {
		return voteOptionDao;
	}

	public void setVoteOptionDao(IVoteOptionDao voteOptionDao) {
		this.voteOptionDao = voteOptionDao;
	}
	
}

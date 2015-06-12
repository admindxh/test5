package com.rimi.ctibet.web.service.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IMemberInfoDao;
import com.rimi.ctibet.web.dao.IScoreManagerDao;
import com.rimi.ctibet.web.service.IScoreManagerService;

@Transactional
@Service("scoreManagerService")
public class ScoreManagerServiceImpl  extends BaseServiceImpl<ScoreManager> implements IScoreManagerService{
	
	
	@Resource(name="scoreManagerDao")
	private IScoreManagerDao scoreManagerDao;
	@Resource
	private IContentDao contentDao;
	@Resource
	private IMemberInfoDao memberInfoDao;

	public IScoreManagerDao getScoreManagerDao() {
		return scoreManagerDao;
	}

	public void setScoreManagerDao(IScoreManagerDao scoreManagerDao) {
		this.scoreManagerDao = scoreManagerDao;
	}

    @Override
    public int updateMemberScoreByMemberCode(String memberCode, String type) {
        //增加的积分
        int returnScore = 0;
        int todayScore = contentDao.getMemberTodayScore(memberCode);
        ScoreManager scoreManager = scoreManagerDao.getScoreManagerByType(type);
        Integer max = scoreManager.getScoremax(); //20
        Integer score = scoreManager.getScorecount();//1224
        //剩余可用积分
        int res = max - todayScore;
        if(res>0){
            if(res >= score){
                //可用积分大于等于分直接加分
                memberInfoDao.updateMemberInfoScore(memberCode, score);
                returnScore = score;
            }else{
                //分大于可用积分直接加可用积分
                memberInfoDao.updateMemberInfoScore(memberCode, res);
                returnScore = res;
            }
        }else{
            returnScore = 0;
        }
        return returnScore;
    }
	
	
	
	
}

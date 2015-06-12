package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.ScoreManager;

public interface IScoreManagerService extends BaseService<ScoreManager> {

    /**
     * 通过会员code和积分类型来增加积分
     * @param memberCode
     * @param type
     */
    public int updateMemberScoreByMemberCode(String memberCode, String type);
    
}

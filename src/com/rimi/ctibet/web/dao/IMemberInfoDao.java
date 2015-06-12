package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.MemberInfo;

public interface IMemberInfoDao extends BaseDao<MemberInfo> {
    /**
     * 通过会员code更新
     * @param memberCode
     */
    public void updateMemberInfoScore(String memberCode, int scroe);
}

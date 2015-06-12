package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.Reply;

public interface IReplyDao extends BaseDao<Reply> {

	/**
	 * 通过帖子code物理删除帖子和贴回复以及子回复的关联关系
	 * @param postCode
	 * @return
	 */
    @Deprecated
	public int deletePostByPostCode(String postCode);
	
    /**
     * 通过code 删除详情页回复
     * @param postCode
     */
	public void deleteReplyByPostCode(String postCode);
	
}

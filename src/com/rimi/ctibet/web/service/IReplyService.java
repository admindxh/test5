package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.Reply;

public interface IReplyService extends BaseService<Reply> {
	/**
	 * 通过指定字段物理删除数据
	 * @param properrt
	 * @param code
	 * @return
	 */
	public int deleteByProperty(String properrt, String code);
	
	public List<Reply> findByProperty(String propertyName,Object value);
	
	/**
     * 通过code 删除详情页回复
     * @param postCode
     */
    public void deleteReplyByPostCode(String postCode);
	
}

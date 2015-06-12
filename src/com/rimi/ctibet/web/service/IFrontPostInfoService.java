package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;

public  interface IFrontPostInfoService  extends BaseService<Content> {

	/**
	 * 查找帖子或者内容 对象
	 * @param code
	 * @param contentType
	 * @return
	 */
	public Content  findByCodeContentType(String code, String contentType);
	
	/**
	 * 根据帖子查找所有回复对象
	 * @param code 帖子code 
	 * @param contentType
	 * @return
	 */
	public  Pager  findbyFatherCode(String code, String contentType,Pager pager);
	
	
	
	/**
	 * 查询 板块和标题和 查看数和回复数
	 * @param code 帖子code 
	 * @param contentType
	 * @return
	 */
	public  List<Map<String,Object>>  findToInfo(String code, String contentType);
	
	/**
	 * 通过会员code 查询帖子列表
	 * @param createusercode
	 * @return
	 */
	public   Pager findbyMemberCode(String createusercode,Pager  pager);
}

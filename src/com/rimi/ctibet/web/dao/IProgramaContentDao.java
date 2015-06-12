package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.ProgramaContent;

public interface IProgramaContentDao extends BaseDao<ProgramaContent> {
	
	/**
	 * 通过栏目code和内容code来删除栏目和内容的映射关系
	 * @param programaCode
	 * @param contentCode
	 * @return
	 */
	public int deleteProgramaContentByProCodeConCode(String programaCode, String contentCode);
	
	
}

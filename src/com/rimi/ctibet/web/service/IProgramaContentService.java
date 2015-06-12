package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.ProgramaContent;

public interface IProgramaContentService extends BaseService<ProgramaContent> {

	/**
	 * 通过栏目code和内容code来删除栏目和内容的映射关系
	 * @param programaCode
	 * @param contentCode
	 * @return
	 */
	public int deleteProgramaContentByProCodeConCode(String programaCode, String contentCode);
	
}

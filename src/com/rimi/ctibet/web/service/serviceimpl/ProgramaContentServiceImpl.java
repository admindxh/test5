package com.rimi.ctibet.web.service.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.ProgramaContent;
import com.rimi.ctibet.web.dao.IProgramaContentDao;
import com.rimi.ctibet.web.service.IProgramaContentService;

@Service("programaContentService")
@Transactional
public class ProgramaContentServiceImpl extends BaseServiceImpl<ProgramaContent> implements IProgramaContentService {
	@Resource IProgramaContentDao programaContentDao;

	/**
	 * 通过栏目code和内容code来删除栏目和内容的映射关系
	 * @param programaCode
	 * @param contentCode
	 * @return
	 */
	public int deleteProgramaContentByProCodeConCode(String programaCode, String contentCode){
		return programaContentDao.deleteProgramaContentByProCodeConCode(programaCode, contentCode);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IProgramaContentDao getProgramaContentDao() {
		return programaContentDao;
	}
	public void setProgramaContentDao(IProgramaContentDao programaContentDao) {
		this.programaContentDao = programaContentDao;
	}

}

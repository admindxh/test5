package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;

public interface IProgramaDao extends BaseDao<Programa>{
	
	public Programa findByCode(String code) ;
	//按照rank获取栏目（降序排列）
	public Pager getProgramaOrderByRank(Pager pager);
	//删除栏目，将栏目available改为0,并且将中间表中相关proCode设置为0
	public void deletePrograma(Programa programa);
    //获取顶层的programa
	public List<Programa> getTopPrograma();
	//根据顶层programa code获取子content
	public List<Programa> getSendPrograma(String code);
	//根据programa code 获取其content
	public List<Map<String,Object>> getContentByProgramaCode(String code);
	//通过栏目父CODE找到子栏目
	public List<Programa> getProgramaByPCode(String programaCode);
	
    /**
     * 获取评价回复栏目
     */
    public List<Programa> getReplyManagePrograma();
    /**
     * 通过栏目类型获取评价回复栏目
     */
    public Programa getReplyManageProgramaByType(String type);
	public Pager findByParent(Pager pager,Programa parentPrograma,String keyword);
	
	public List<Programa> findByParent(Programa condition);
	public Pager findByOrder (Pager pager, Programa programa);
}

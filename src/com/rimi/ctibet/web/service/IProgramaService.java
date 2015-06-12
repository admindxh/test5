package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Programa;

public interface IProgramaService {
       public void savePrograma(Programa programa);
       public void deletePrograma(Programa programa);
       public void updatePrograma(Programa programa);
       public Programa getProgramaByCode(String code);
       /**
        * 根据 code物理删除 
        * @param programa
        */
       public void delete(Programa programa);
       /**
        * 根据code删除
        * @param code
        */
       public void delete(String code);
       //按照rank获取栏目（降序排列）
       public Pager getProgramaOrderByRank(Pager pager);
       //获取顶层programa
       public List<Programa> getTopPrograma();
       //根据顶层programa code获取子programa
       public List<Programa> getSendPrograma(String code);
       //根据programa code 获取其content
       public List<Map<String,Object>> getContentByProgramaCode(String code);
       //获取当前用户programa所发帖子类型
       public List<Programa> findProByMemberCode(String sql,List param);
       /**
        * 获取评价回复栏目
        */
       public List<Programa> getReplyManagePrograma();
       
       /**
        * 更新评论回复设置
        */
       public void updateReplyManageSetting(List<Programa> listPrograma);
       
       /**
        * 评论类型获得是否开启评论（0关1开）
        */
       public int isOpenReply(String type);
	public Pager findByParent(Pager pager, Programa parentPrograma,
			String keyword);
	
	/**
	 * 验证装备类别是否重复
	 * 
	 * @param programa 查询装备推荐下是否含有相同的名称
	 * @return 是否存在
	 */
	public int validateNameIsRepeat (Programa programa);
	
	public Pager findByOrder (Pager pager, Programa programa);
}

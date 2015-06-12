package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;

/**天上社区板块管理dao
 * @author xiaozhen
 */
public interface IPlateDao extends BaseDao<Programa>{
	
	//根据code获取板块
	public Programa findByCode(String code) ;
	//保存板块
	public void savePlate(Programa programa);
	//删除板块，将栏目available改为0,并且将中间表中相关proCode设置为0
	public void deletePlate(Programa programa);
	//修改板块
	public void updatePlate(Programa programa);
    //获取顶层的programa(板块)
	public List<Programa> getTopPlate();
	//获取到普通的社区板块（除开全站置顶区和骑行专区）
	public List<Programa> getSendPrograma();
	//根据programa code 获取其content
	public List<Map<String,Object>> getContentByProgramaCode(String code);
	//获取门户天上社区首页
	public List<Map<Programa,List<Content>>> getProtalPlate();
	//后台板块列表
	public Pager plateList(Pager pager);
	
	//门户天上社区首页展示
	public List<Map<String,Object>> getFrontTsSqShow(String plateCode,int isTop);
	public List<Programa> getProList();
	//获取全站置顶区t帖子数量
	public String postCountByIsTop();
}

package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;

public interface IPraiseService extends BaseService<Praise> {
	/**
	 * 赞和浏览查看的保存
	 * @param praise
	 */
	public void savePraise(Praise praise);
	
	/**
	 * 修改赞
	 * @param praise
	 */
	public void updatePraise(Praise  praise);
	
	
	/**
	 * 更新赞的数量 +1
	 * @param contentCode
	 */
	public  void updatePraiseCount(String contentCode);
	
	
	/**
	 * 取消赞的数量  -1
	 * @param contentCode
	 */
	public  void cancelPraiseCount(String contentCode);
	
	/**
	 * 更新浏览的数量 +1
	 * @param contentCode
	 */
	public  void updateViewCount(String contentCode);
	
	
	/**
	 * 查询最赞帖子列表调用   contentServiceImpl findContentByProCode 注意 栏目code  保存最赞  code 栏目
	 * 
	 */
	
	public Pager findContentByProCode(Pager pager, String proCode);
	
	/**
	 * 保存最赞帖子列表调用   contentServiceImpl saveContentAndProgramaContent 注意 栏目code  保存最赞  code 栏目
	 */
	
	public void saveContentAndProgramaContent(Content content, String programaCode);
	
	/**
	 * 删除 最赞帖子列表调用   contentServiceImpl saveContentAndProgramaContent 注意 栏目code  保存最赞  code 栏目
	 */
	public void deleteContentAndProgramaContent(String[] contentCodes);
	
	/**
	 * 找到赞
	 * @param praise
	 * @return
	 */
	public Praise  findPraise(Praise praise);
			
	/**
	 * 查询赞列表数据 分页
	 * @param sql
	 * @param params
	 * @param pager
	 * @return
	 */
	public Pager findPraiseList(List<Object> params, Pager pager);
	
	/**
	 * 指定数量查询最赞回复数量
	 * @param row
	 * @param params
	 * @return
	 */
	
	public  List<Map<String, Object>> getPraiseList(int row,List<Object> params);
	
	/**
	 * 获取回复最多的帖子
	 * @param row
	 * @param params
	 * @return
	 */
	public  List<Map<String, Object>> getPraiseListReply(int row,List<Object> params);
	
	/**
	 * 验证是否存在赞和浏览数据
	 * @param coentcode
	 * @return
	 */
	public boolean isValidExitPraise(String coentcode);
	
	/**
	 * 通过contentCode获取Praise
	 * @param contentCode
	 * @return
	 */
	public Praise getPraiseContentCode(String contentCode);
	
	public Object[] searchPVCount(String postCode) ;
	
	/**
	 * 更新查看数量 +1
	 */
	public void updateViewCountByContentCode(String contentCode);
	
	public void updateFavateCount(final String contentCode);
   /**
    * 按照查看数desc排序
    * @param plist
    * @return
    */
	public List<Praise> orderByViewCount(List<Praise> plist);
    	
    	
    
}


package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;

/**
 * 赞 dao
 * @author dengxh
 *
 */
public interface IPraiseDao  extends BaseDao<Praise>{
	
	
	/**
	 * 赞和浏览查看的保存
	 * @param praise
	 */
	public void savePraise(Praise praise);
	
	/**
	 * 后台修改赞
	 * @param praise
	 */
	public void updatePraise(Praise  praise);
	
	
	/**
	 * 前台  更新赞的数量 +1
	 * @param contentCode
	 */
	public  void updatePraiseCount(String contentCode);
	
	
	/**
	 *前台   取消赞的数量  -1
	 * @param contentCode
	 */
	public  void cancelPraiseCount(String contentCode);
	
	/**
	 * 前台 更新浏览的数量 +1
	 * @param contentCode
	 */
	public  void updateViewCount(String contentCode);
	
	/**
	 * 前台 更新收藏的数量 +1
	 * @param contentCode
	 */
	public  void updateFavateCount(String contentCode);
	
	/**
	 * 前台 更新评论的数量 +1
	 * @param contentCode
	 */
	public  void updateReplyCount(String contentCode);
	
	/**
	 *   查询最赞帖子列表调用   contentServiceImpl findContentByProCode 注意 栏目code  保存最赞  code 栏目
	 * 
	 */
	
	public Pager findContentByProCode(Pager pager, String proCode);
	
	/**
	 * 保存最赞帖子列表调用   contentServiceImpl saveContentAndProgramaContent 注意 栏目code  保存最赞  code 栏目
	 */
	
	public void saveContentAndProgramaContent(Content content, String programaCode);
	/**
	 * 保存最赞帖子列表调用   contentServiceImpl deleteContentAndProgramaContent 注意 栏目code  保存最赞  code 栏目
	 */
	
	public void deleteContentAndProgramaContent(String[] contentCodes);
	
	/**
	 * 找到赞,封装 praise 对象  放入帖子
	 * @param praise
	 * @return
	 */
	public Praise  findPraise(Praise praise);
	
	
	/**
	 * 后台 查询最赞回复列表分页
	 * @param sql
	 * @param params
	 * @param pager
	 * @return
	 */
	public Pager findPraiseList(List<Object> params, Pager pager);
	
	
	/**
	 * 前台 指定数量查询最赞回复数量
	 * @param row
	 * @param params
	 * @return
	 */
	
	public  List<Map<String, Object>> getPraiseList(int row,List<Object> params);
	
	
	/**
	 * 前台 获取回复最多的帖子
	 * @param row
	 * @param params
	 * @return
	 */
	public  List<Map<String, Object>> getPraiseListReply(int row,List<Object> params);
	
	/**
	 * 获取赞最多的帖子
	 * @param row
	 * @param params
	 * @return
	 */
	public  List<Map<String, Object>> getPraiseListPost(int row,List<Object> params);
	
	
	
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
	
	/**
	 * 查询回复数量和查看数量
	 * @param postCode
	 * @return
	 */
	public  Object[]  searchPVCount(String postCode);
	
	
	
}


package com.rimi.ctibet.web.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.portal.controller.vo.FrontViewAndDesVo;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;

public interface IContentDao extends BaseDao<Content> {
	/**
	 * 通过code和内容类型查找有效数据
	 * 
	 * @param code
	 * @param contentType
	 * @return
	 */
	public Content findByCodeContentType(String code, String contentType);

	/**
	 * 通过内容类型查找有效数据 分页
	 */
	
	
	public Pager findByContentType(String contentType, Pager pager);
	/**
	 * 	/**
	 * 按某种规则 查询 <br>
	 * 规则：1.某种内容类型 ：类型参见culture类，若类型不在culture类，中请直接传入类型 字符串<br>
	 * 2.某种排序规则： 排序规则请参见Culture类中，若排序规则不在请Culture类中，可开包此类<br>
	 * 3.某种搜索规则： 会根据content的属性 自动取值，<br>
	 * 如content.title=“西藏;拉萨”返回 title中含有 “西藏”或“拉萨”关键词的数据 <br>
	 * 若需要,请先用关键字和 属性做积。<br>
	 * 4.某种页码规则：请参见Pager类设计<br>
	 * 5.以上规则可做笛卡尔积
	 * @param pager
	 *            页码信息（返回值和此pager为同一引用）
	 * @param content内容 （搜索规则）
	 * @param type 内容类型
	 * @param orderField 排序规则编号（注意传入的是规则的编号）
	 * @return 一页数据,即Content的list，content 中不带有 others 数据
	 */
	
	public Pager findContent(Pager page,Content content,String type,  String orderField);

	/**
	 * 通过视频类型和标题查询有效数据 分页
	 * 
	 * @param content
	 * @return
	 */
	public Pager findVedioByProgramaCodeTitle(Content content, Pager pager);

	/**
	 * 通过帖子code删除帖子，并删除其回复和子回复
	 * 
	 * @param postCode
	 */
	public int deletePostByPostCode(String postCode);

	/**
	 * 通过回复code删除回复，并删除其子回复
	 * 
	 * @param replyCode
	 */
	public int deleteReplyByReplyCode(String replyCode);

	/**
	 * 通过栏目内容中间表中的栏目code获取content
	 * 
	 * @return
	 */
	public List<Content> findContentByProCode(String proCode);

	/**
	 * 通过栏目内容中间表中的栏目code获取指定行数的content
	 * 
	 * @return
	 */
	public List<Content> findContentByProCodeRow(String proCode, int row);

	/**
	 * 通过栏目内容中间表中的栏目code获取content 分页
	 * 
	 * @return
	 */
	public Pager findContentByProCode(Pager pager, String proCode);

	/**
	 * 通过 审核状态、版块、目的地、景点、官方标记、[会员名、手机、邮箱、攻略标题(keyWord)]、攻略有效性 查询攻略列表
	 * 
	 * @param state
	 *            审核状态
	 * @param proCode
	 *            板块
	 * @param destinationCode
	 *            目的地
	 * @param viewCode
	 *            景点
	 * @param isOfficial
	 *            官方标记
	 * @param keyWord
	 *            关键字[会员名、手机、邮箱、攻略标题]
	 * @param avaliable
	 *            有效性
	 * @return
	 */
	public Pager searchStrategyByContentMember(Pager pager,
			Map<String, Object> paramMap,String order);

	/**
	 * 通过code更新审核状态
	 * 
	 * @param state
	 * @return
	 */
	public int updateState(String code, int state);

	/**
	 * 查询后台 专题列表
	 * 
	 * @param name
	 * @param time
	 * @param orderBy
	 * @return
	 */
	public Pager searchSpecailListByNameTime(Pager pager, String name,
			String time, int orderBy);

	/**
	 * 
	 * @param string
	 * @param code
	 * @return
	 */
	public List<Content> findContentByDestinationCodeAndType(String code,
			String type);

	/**
	 * 查出其他信息
	 * 
	 * @param code
	 * @return
	 */
	public Map findContentOthers(String code);

	/**
	 * 获取游西藏首页的推荐景点
	 * 
	 * @return
	 */
	public Content findTravelView();

	void test();
	/**
	 *  前台根据攻略查询分页
	 * @param pager
	 * @param order
	 * @param programCode
	 * @return
	 */
	public Pager findPagerTravel(Pager pager,Integer order,String programCode,String des,String view,String keyword,Integer isOffical );
	
	/**
	 * 查询攻略信息
	 * @param code
	 * @return
	 */
	public TravalFrontPageVo getTravalFrontPageVo(String code);
	
	/**
	 * 查询回复对象
	 * @param postCode
	 * @return
	 */
	public Pager getFrontReplyVo(Pager pager,String postCode);
	
	/**
	 * 查询相关的景点的攻略
	 * @param row
	 * @param code
	 * @return
	 */
	public List<TravalFrontPageVo>  getStrageList(Integer row,String code);
	
	/**
	 * 查询相关的目的地和景点
	 * @param code
	 * @return
	 */
	public List<FrontViewAndDesVo>  getFrontViewAndDesList(String searchType,String code);
	/**
	 * 通过 postCode和审核状态 内容类型获取回复列表
	 */
	public Pager getReplyByPostCodeState(Pager pager, String postCode, String contentType, int state);
	//根据状态或者模块获取当前用户的攻略
    public Pager getMemberStraByState(String memberCode,String state,String programaCode,Pager pager);
    
    
    /**
     * 查询评论量
     * @param start
     * @param end
     * @return
     */
    public Integer getReplyCount(Date start,Date end);
    
    


	public void  updateOrSaveOthers(String contentCode, Map map);
    
    /**
     * 通过评论类型和审核状态搜索 评论回复管理列表
     */
    public Pager searchReplyByContentTypeState(Pager pager, String contentType, int state);
    /**
     * 通过评论类型和审核状态查询 数据行数
     */
    public int findReplyNumByContentTypeState(String contentType, int state);
    
    
    
    /**
     * 查询攻略量总的  用户上传
     * @param start
     * @param end
     * @return
     */
    public Integer getStrageCount(Date start,Date end);
    /**
     * 通过栏目code获取content
     * @param programaCode
     */
	public List<Content> findContentByProgramaCode(String programaCode);
    
	/**
	 * 通过会员code获取今天的积分
	 */
    public int getMemberTodayScore(String memberCode);
    
    /**
     * 获取专题最大序号
     * @return
     */
    public int getSpecialMaxSortNum();
    
    /**
     * 通过详情页 数据code逻辑删除评论
     */
    public void deleteReplyByPostCodeLogical(String postCode, String contentType);

	public Pager findOtherContent(Pager pager, Content content, String contentType,
			String orderType);
    
}

package com.rimi.ctibet.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.vo.FrontViewAndDesVo;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;

public interface IContentService extends BaseService<Content> {
	
	/**
	 * 保存内容和栏目内容中间表
	 * 如果栏目code为空则只保存内容
	 * @param content
	 * @param programaCode
	 */
	public void saveContentAndProgramaContent(Content content, String programaCode);
	
	/**
	 * 删除内容和 栏目内容关联关系
	 * @param content
	 */
	public void deleteContentAndProgramaContent(Content content);
	/**
	 * 批量删除内容和 栏目内容关联关系
	 * @param contentCodes
	 */
	public void deleteContentAndProgramaContent(String[] contentCodes);
	/**
	 * 删除一组数据，codes不能为null
	 * @param Content codes
	 */
	public void deleteByCodes(List<String> codes);
	public void deleteByCodes(String[] codes);
	/**
	 * 保存攻略 和栏目内容中间表
	 * 如果栏目code为空则只保存内容
	 * @param content
	 * @param programaCode
	 * @param views(需要code 和 destinationCode属性)
	 * @param destinationCodes
	 */
	public void saveStrategy(Content content, String programaCode, String[] viewCodes, String[] destinationCodes);
	/**
	 * 修改攻略
	 * @param content
	 * @param viewCodes
	 * @param destinationCodes
	 */
	public void updateStrategy(Content content, String[] viewCodes, String[] destinationCodes, String contextPath);
	
	/**
	 * 通过攻略code删除攻略以及攻略景点目的地的关联
	 * @param code
	 */
	public void deleteStrategyByCode(String code);
	/**
	 * 通过攻略code批量删除攻略以及攻略景点目的地的关联
	 * @param code
	 */
	public void deleteStrategyByCode(String[] codes);
	
	/**
	 * 保存回复
	 * @param content
	 * @param postCode	帖子code
	 */
	public void saveReply(Content content, String postCode);
	
	/**
	 * 通过code和内容类型查找有效数据
	 * @param code
	 * @param contentType
	 * @return
	 */
	public Content findByCodeContentType(String code, String contentType);
	
	/**
	 * 通过视频类型和标题查询有效数据
	 * @param content
	 * @return
	 */
	public Pager findVedioByProgramaCodeTitle(Content content, Pager pager);
	
	/**
	 * 通过帖子code删除帖子，并删除其回复和子回复 和关联关系 
	 * @param postCode
	 */
	/**
	 * 通过回复code删除回复，并删除其子回复 和关联关系
	 * @param replyCode
	 */
	public void deleteReplyByReplyCode(String replyCode);
	/**
	 * 通过子回复code删除子回复，并删除与回复的关联关系
	 * @param subReplyCode
	 */
	public void deleteSubReplyBySubReplyCode(String subReplyCode);
	
	/**
	 * 通过栏目内容中间表中的栏目code获取content
	 * @return
	 */
	public List<Content> findContentByProCode(String proCode);
	/**
	 * 通过栏目内容中间表中的栏目code获取指定行数的content
	 * @return
	 */
	public List<Content> findContentByProCodeRow(String proCode, int row);
	
	/**
	 * 分页查询某类信息 默认排序
	 * @return
	 */
	public Pager findContentsByType(Pager page, Content content);
	
	/**
	 *  搜索 特定类型 ，带特定关键字数据，
	 *  按照 某种规则排序 （排序规则参见Culture类）
	 * @return 返回content 的list 不带 others 信息
	 */
	public Pager search(Pager page,Content content, String orderField);
	/**
	 *  搜索 特定类型 ，带特定关键字数据，
	 *  按照 某种规则排序 （排序规则参见Culture类）
	 * @return 返回content 的list 带 others 信息
	 */
	public Pager searchOnOthers(Pager page,Content content, String orderField);
	
	/**
	 * 通过栏目内容中间表中的栏目code获取content 分页
	 * @return
	 */
	public Pager findContentByProCode(Pager pager, String proCode);
	
	/**
	 * 通过 审核状态、版块、目的地、景点、官方标记、[会员名、手机、邮箱、攻略标题(keyWord)]、攻略有效性 查询攻略列表
	 * @param state	审核状态
	 * @param proCode	板块
	 * @param destinationCode 目的地
	 * @param viewCode	景点
	 * @param isOfficial	官方标记
	 * @param keyWord 关键字[会员名、手机、邮箱、攻略标题]
	 * @param avaliable	有效性
	 * @return
	 */
	public Pager searchStrategyByContentMember(Pager pager, Map<String, Object> paramMap,String order);
	
	/**
	 * 通过code更新审核状态
	 * @param state
	 * @return
	 */
	public void updateState(String code, int state);
	
    /**
     * 通过目的地获取攻略
     * @param destinationCode
     * @return
     */
	public List<Content> findStrategyByDestinationCode(String destinationCode);
	
	/**
	 * 查询后台 专题列表
	 * @param name
	 * @param time
	 * @param orderBy
	 * @return
	 */
	public Pager searchSpecailListByNameTime(Pager pager, String name, String time, int orderBy);
	
	/**
	 * 保存专题
	 * @param content
	 */
	public void saveSpecial(Content content);
	/**
	 * 修改专题
	 * @param content
	 */
	public void updateSpecial(Content content, Praise praise);
    /**
     * 通过目的地以及类型获取内容
     * @param code
     * @return
     */
	public List<Content> findContentByDestinationCodeAndType(String code,String type);
    /**
     * 保存游西藏首页的景点code
     * @param viewcode
     */
	public void saveTravelViews(String viewcode);
	/**
	 * 获取游西藏首页推荐景点
	 * @return
	 */
	public List<View> getTravelView();
	
	public Content getByCode(String code);
	
	/**
	 * 查询 攻略 热门 等下信息
	 * @param pager
	 * @param order
	 * @param programCode
	 * @return
	 */
	public Pager findPagerTravel(Pager pager, Integer order, String programCode,String des,String view,String keyword,Integer isoffical) ;
	
	/**
     * 通过 postCode和审核状态获取回复列表
     */
	public Pager getReplyByPostCodeState(Pager pager, String postCode, String contentType, int state);
	
    public TravalFrontPageVo getTravalFrontPageVo(String code);
    
    /**
     * 查询回复对象
     * @param postCode
     * @return
     */
    public Pager getFrontReplyVo(Pager pager,String postCode);
    
    public List<TravalFrontPageVo> getStrageList(Integer row, String code);
    
    public List<FrontViewAndDesVo>  getFrontViewAndDesList(String searchType,String code);
    
  //根据状态或者模块获取当前用户的攻略
    public Pager getMemberStraByState(String memberCode,String state,String programaCode,Pager pager);
   /**
    * 查找 content 附带
    * @param code 内容code
    * @return  others 信息 
    */
	public Map findOthers(String code);

boolean updateOthers(String contentCode, Map map);


  //根据回复类型查询各模块用户的评论和回复
    public Pager getMyReplyByType(String sql, List params,Pager pager);
    
    
    /**
     * 查询回复数量
     * @param start
     * @param end
     * @return
     */
    public Integer getReplyCount(final Date start, final Date end);
    
    
    /**
     * 通过评论类型和审核状态搜索 评论回复管理列表
     */
    public Pager searchReplyByContentTypeState(Pager pager, String contentType, int state);
    /**
     * 通过评论类型和审核状态查询 数据行数
     */
    public int findReplyNumByContentTypeState(String contentType, int state);
    
    /**
     * 查询攻略量
     * @param start
     * @param end
     * @return
     */
    public Integer getStrageCount(final Date start, final Date end) ;
    
    /**
     * 保存图片
     */
    public void updateSeeBanner(List<Content> contentList,String programaCode);
    /**
     * 通过栏目code获取content
     * @param programaCode
     * @return
     */
    public List<Content> findContentByProgrmaCode(String programaCode);
    /**
     * 查询用户回复数
     */
    public int getAllReply(String memberCode);
    /**
     * 通过会员code获取今天的积分
     */
    public int getMemberTodayScore(String memberCode);

    /**
     * 保存攻略中的图片
     * @param content
     * @param destinationCodes
     * @param viewCodes
     */
    public void saveAtlas(String contextPath, Content c);
    
    public void returnPost(Content post); 
    
    public int getMemberStraNum(String memberCode);
    
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

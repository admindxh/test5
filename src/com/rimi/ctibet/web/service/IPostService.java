
package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.portal.controller.vo.TssqPortalPostVo;

public interface IPostService {
	
	
	//根据code获取帖子
	public Content getPostByCode(String code);
	//帖子列表(state标示帖子的状态)
    public Pager UncheckedPostList(Pager pager,int state,String type);
    //保存帖子与回复postCode表示帖子的code
    public void savePost(Content post,String postCode);
    //帖子修改
    public void updatePost(Content post);
    //帖子删除
    public void deletePost(Content post);
    //帖子审核
    public void checkPost(Content post,int state);
    //帖子高级查询
    public Pager searchPost(int state,String contentType ,String programaName,String keyword,Pager pager);
  //查询用户全部帖子（包括发布、回复、收藏）
    public Pager findAllPost(String memberCode,Pager pager);
    //多参数查询
    public Pager findWithPagerByMap(String hql, Map<String, Object> Param, Pager pager);
    public Pager findAllPassPost(Integer state, String programcode,
			String keyword, String order,Pager pager);
    
    //门户帖子列表显示所有
    public Pager getAllPassPostInProtal(Pager pager,String rule,String  plateCode,String isTop); 
    //根据帖子的code得到旗下所有回复和回复人的信息
    public Pager  getReplysInfoByPostCode(String code,Pager pager);
    //获取帖子的详细
    public Map<String,Object> getPostDetailByCode(String postCode);
    //==================================================门户首页相关===============================================
    //1.置顶专区数据
    public List<Map<String,Object>> getTopPost();
    //2.最赞回复的数据
	public List<Map<String,Object>> getBestPraise();
	//3.自驾骑行的数据
	//3.1骑行公告数据
	public List<Map<String,Object>> getDrivAannouncements();
	//3.2骑行故事数据
	public List<Map<String,Object>> getDriveStorys();
	//3.3骑友征集数据
	public List<Map<String,Object>> getDriveRecruits();
	//3.4装备讨论数据
	public List<Map<String,Object>> getDriveEquipments();
	//4.回复最多与被赞最多的数据
	public List<Map<String,Object>> getMostRplys();
	public List<Map<String,Object>> getMostPraise();
	//5.攻略&游记，出行搭伴，公益活动的数据
	public List<TssqPortalPostVo> normalPost();
    //根据状态或者模块获取当前用户的帖子
    public Pager getMemberPostByState(String memberCode,String state,String programaCode,Pager pager);
    //图说西藏的评论分页
    public Pager getMutiPost(Pager pager,String mutiCode);
    //查找图集的评论
	public List<Content> findPostByMutiCode(String mutiCode);
    //获取待审核帖子与回复总数
	public int getUncheckedPostAndReplyNum();
	//积分排行前五
	public List<Map<String,Object>> getTopFive();
	//发帖排行前五
	public List<Map<String,Object>> getTopFiveBuPCount();
	//根据发帖人的code获取总的发帖数
	public String postCountByMemCode(String mCode);
	//根据本级ip获取所有的点赞code
	public List<String> praiseCode(String ip);
	/**
     * 获取指定数量的最多评论数帖子
     */
    public List<Map<String, Object>> getMostReply(int row);
    
    /**
     * 栏目code查帖子数
     */
    public int getPostCountByPrograma(String p);
    /**
     * 栏目code查帖子查看数
     */
    public int getPostcheckNumByPrograma(String p); 
    /**
     * 栏目code查帖子回复数
     */
    public int getPostReplyNumByPrograma(String p); 
    /**
     * 栏目code查帖子最新回复
     */
    public Content getPostNewReplyByPrograma(String p); 
    
    /**
     * 通过板块code 排序类型 数据行数获取帖子基本信息
     */
    public List<PostVo> getPostByPrograma(String programCode, int orderType, int row);
    
}

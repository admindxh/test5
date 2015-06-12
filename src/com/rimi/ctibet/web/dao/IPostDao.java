package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.portal.controller.vo.TssqPortalPostVo;


/**帖子管理接口
 * @author xiaozhen
 *1.待审核的帖子及回复
 *2.已审核的帖子及回复
 */
public interface IPostDao extends BaseDao<Content>{

	
	//帖子及回复 列表(state标示帖子的状态，type标示类型)
    public Pager UncheckedPostList(Pager pager,int state,String type);
    //保存及回复帖子
    public void savePost(Content post,String postCode);
    //帖子及回复修改
    public void updatePost(Content post);
    //帖子及回复删除
    public void deletePost(Content post);
    //帖子及回复审核
    public void checkPost(Content post,int state);
    //帖子及回复高级查询(state审核状态,contentType类型,programaCode栏目)
    public Pager searchPost(Pager pager,int state,String contentType,String programaCode,String keyword);
   //按照code查询帖子及回复
    public Content getPostByCode(String code);
    //查询用户全部帖子（包括发布、回复、收藏）
    public Pager findAllPost(String memberCode,Pager pager);
    ///查询审核通过的帖子
    public Pager  findAllPassPost(Integer state,String programcode,String keyword,String order,Pager pager);
    //门户帖子列表显示
    public Pager getAllPassPostInProtal(Pager pager,String rule,String plateCode,String isTop);
    //根据帖子的code得到旗下所有回复和回复人的信息
    public Pager  getReplysInfoByPostCode(String code,Pager pager);
    //获取帖子详细
    public Map<String,Object> getPostDetailByCode(String postCode);
    //=======================================门户首页相关==============================================
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
    //获取图说西藏分页
	public Pager getMutiPost(Pager pager, String mutiCode);
	//获取所有评论
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

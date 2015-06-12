package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.GroupTravelVO;

public interface IGroupTravelDao extends BaseDao<GroupTravel> {

	// 高级查询（关键字，目的地，景点）
	public Pager searchGroupTravel(Pager pager, String keyWord,
			String destinationCode, String viewCode, String isRecommend);

	// 高级排序（查看数，评论数，收藏数）
	public Pager orderByGroupTravel(Pager pager, String rule);

	// =======编辑:1.获取相关景点，2.获取相关目的地=======
	public List<View> getViews(String groupTravelCode);

	public List<Destination> getDestinations(String groupTravelCode);
	
	/**
	 * 获取最新官方推荐团游
	 * @param viewCode
	 */
	public List<GroupTravelVO> getGroupTravelRecommendByViewCode(int row, String viewCode);
	/**
	 * 获取最新官方推荐团游
	 * @param viewCode
	 */
	public List<GroupTravelVO> getGroupTravelRecommendBydestinationCode(int row, String destinationCode);

	// 商户首页显示団游信息
	public List<Map<String, Object>> getGtForMIndex();
	  //获取商户首页団游列表
    public Pager getGroupTravelList(Pager pager,String destCode,String viewCode,String keyWord,String rule);
    //団游回复
    public Pager gtReplyInfo(Pager pager, String gtCode);
    
    
    /**
     * 通过团游code获取商户评分
     */
    public float getScore(String code);
    
}

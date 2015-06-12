package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.VoteOption;
import com.rimi.ctibet.web.controller.vo.ActivetyTotalVo;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;

public interface IActivityService extends BaseService<Activity> {

	/** 保存活动 */
	public void saveActivity(Activity activity, EnrollNotice enrollNotice, String options[], List<EnrollForm> listEnrollForm, List<Image> listImage);
	/** 修改活动 
	 * @throws Exception */
	public void updateActivity(Activity activity, EnrollNotice enrollNotice, List<VoteOption> listVoteOption, List<EnrollForm> listEnrollForm, List<Image> listImage) throws Exception;
	
	/**
	 * 搜索活动信息管理列表
	 * @param name 活动名
	 * @param block	包含模块（上传、报名、投票、报名支付、支付 ）
	 * @param isEnd
	 * @param orderby
	 * @return
	 */
	public Pager searchActivityByName(Pager pager, String name, int block, int isEnd, int orderby);
	
	/**
	 * 删除活动
	 * @param code
	 */
	public void deleteActivity(String code);
	
	/**
	 * 批量删除活动
	 * @param code
	 */
	public void deleteActivity(String[] codes);
	
	/**
	 * 按指定排序方式获取数据
	 * @param orderby
	 * @return
	 */
	public Pager getAllActivityOrder(Pager pager, int orderby, boolean isShowNotBegin, boolean isShowNotOffical);
	
	/**
	 * 通过活动code获取活动信息
	 * @param code
	 * @return
	 */
	public Activity getActivityByCode(String code);
	
	/**
	 * 活动查看数+1
	 * @param activityCode
	 */
	public void activityCheckNumPlusOne(String activityCode);
	
	/**
	 * 指定字段 数量+1
	 * @param columns（字段，可以多个）
	 */
	public void updateActivityNumByColum(String activityCode, String[] columns);
	
	/**
	 * 更新活动伪查看数和伪投票数
	 * @param activity
	 * @param listVoteOption
	 */
	public void updateActivityCheckNumVoteNum(Activity activity, List<VoteOption> listVoteOption);
	/**
	 * 根据参数查询并分页
	 * @param hql
	 * @param Param
	 * @param pager
	 * @return
	 */
	public Pager findPagerBySQL(String sql, final List params,Pager pager);
	
	
	/**
	 * 通过时间查询活动
	 * @param start
	 * @param end
	 * @return
	 */
	public Pager findPagerByTime(Pager pager,String start,String end);
	
	/**
	 * 查询总的统计
	 * @param start
	 * @param end
	 * @return
	 */
	public  ActivetyTotalVo  getActivetyTotal(String start,String end);
	
	public  Pager  getActivetyTotalPager(String start,String end,Pager pager);
	
	/**
     * 获取最近参与列表 包含最近报名和上传作品
     * @return
     */
    public List<MemberEnrollDetailVO> getJoinMemberList(int row);
    
    /**
     * 获取最大id
     * @return
     */
    public int getMaxNum();
    
}

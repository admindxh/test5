package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;

public interface IMemberEnrollDetailDao extends BaseDao<MemberEnrollDetail> {
	/**
	 * 获取最新报名活动会员列表
	 * @param isTop 是否按置顶数据排序
	 * @return
	 */
	public List<MemberEnrollDetailVO> getNewEnrollMemberByActivityCodeRow(String activityCode, int row, boolean isTop);
	
	/**
	 * 获取最新报名活动会员列表 分页
	 * @param isTop 是否按置顶数据排序
	 * @return
	 */
	public Pager getNewEnrollMemberByActivityCode(Pager pager, String activityCode, String orderChannelSourceCode, boolean isTop);
	
	/**
	 * 通过活动code和会员code获取报名信息
	 * @param activityCode
	 * @param memberCode
	 * @return
	 */
	public List<MemberEnrollDetailVO> getMemberEnrollDetailByActCodeMemberCode(String activityCode, String memberCode);
	
	/**
	 * 通过活动code和会员code 删除报名信息
	 * @param activityCode
	 * @param memberCode
	 */
	public void deleteMemberEnrollDetailByActCodeMemberCode(String activityCode, String memberCode);
	
	/**
	 * 通过活动code和会员code 置顶
	 * @param activityCode
	 * @param memberCode
	 */
	public void updateMemberEnrollDetailIsTop(String activityCode, String memberCode, int isTop);
	
	/**
	 * 通过活动code字段code会员code 查询
	 */
	public MemberEnrollDetail getMemberEnrollDetailByActCodePropertyCodeMemberCode(String activityCode, String propertyCode, String memberCode);
}

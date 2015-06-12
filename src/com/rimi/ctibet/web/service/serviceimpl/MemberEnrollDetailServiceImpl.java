package com.rimi.ctibet.web.service.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.dao.IActivityDao;
import com.rimi.ctibet.web.dao.IEnrollFormDao;
import com.rimi.ctibet.web.dao.IMemberEnrollDetailDao;
import com.rimi.ctibet.web.dao.IOrderDao;
import com.rimi.ctibet.web.dao.IUserFavoriteDao;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
@Transactional
@Service("memberEnrollDetailService")
public class MemberEnrollDetailServiceImpl extends BaseServiceImpl<MemberEnrollDetail> implements IMemberEnrollDetailService {

	@Resource IMemberEnrollDetailDao memberEnrollDetailDao;
	@Resource IEnrollFormDao enrollFormDao;
	@Resource IActivityDao activityDao;
	@Resource IOrderDao orderDao;
	@Resource IUserFavoriteDao userFavoriteDao;

	/**
	 * 保存多个表单信息
	 * @param listMemberEnrollDetail
	 */
	public void saveMemberEnrollDetail(String activityCode, String OCS, String memberCode, List<MemberEnrollDetail> listMemberEnrollDetail){
		int i=0;
		Activity activity = activityDao.findByCode(activityCode);
		for (MemberEnrollDetail memberEnrollDetail : listMemberEnrollDetail) {
			if(i==0){
				//活动报名数+1
				activityDao.updateActivityNumByColum(memberEnrollDetail.getActivityCode(), new String[]{"enrollNum"});
				i++;
			}
			EnrollForm enrollForm = enrollFormDao.findByCode(memberEnrollDetail.getEnrollFormCode());
			if(enrollForm!=null && enrollForm.getPropertyType().equals(EnrollForm.PROPERTY_TYPE_TEXT)){
			    String bad = KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(memberEnrollDetail.getPropertyValue(), "**");
			    memberEnrollDetail.setPropertyValue(bad);
			}
			memberEnrollDetail.setCode(CodeFactory.createCode("ACTE"));
			memberEnrollDetail.setCreateTime(new Date(System.currentTimeMillis()));
			save(memberEnrollDetail);
		}
		//如果有支付就要创建订单
		if(activity.getIsEnrollPay()==1){
		    Order order = new Order();
		    order.setCode(Order.createCode());
		    order.setActivityCode(activityCode);
		    order.setMoney(activity.getMoney());
		    order.setMemberCode(memberCode);
		    order.setOrderChannelCode("-1");
		    order.setOrderChannelSourceCode(OCS);
		    order.setCreateTime(DateUtil.getCurrentDate());
		    orderDao.save(order);
		    
		    for (MemberEnrollDetail memberEnrollDetail : listMemberEnrollDetail) {
		        memberEnrollDetail.setOrderCode(order.getCode());
		        memberEnrollDetailDao.update(memberEnrollDetail);
            }
		    
		}
		
		//保存参加信息
		UserFavorite userFavorite = userFavoriteDao.getByCode(activityCode, memberCode);
        if(userFavorite==null){
            userFavorite = new UserFavorite();
            userFavorite.setMemberCode(memberCode);
            userFavorite.setCode(activityCode);
            userFavorite.setState("1");
            userFavorite.setType(UserFavorite.User_Fav_Activity);
            userFavorite.setJoinTime(DateUtil.getCurrentDate());
            userFavoriteDao.save(userFavorite);
        }
	}
	
	/**
	 * 获取最新报名活动会员列表
	 * @param isTop 是否按置顶数据排序
	 * @return
	 */
	public List<MemberEnrollDetailVO> getNewEnrollMemberByActivityCodeRow(String activityCode, int row, boolean isTop){
		return memberEnrollDetailDao.getNewEnrollMemberByActivityCodeRow(activityCode, row, isTop);
	}
	
	/**
	 * 获取最新报名活动会员列表 分页
	 * @param isTop 是否按置顶数据排序
	 * @return
	 */
	public Pager getNewEnrollMemberByActivityCode(Pager pager, String activityCode, String orderChannelSourceCode, boolean isTop){
		return memberEnrollDetailDao.getNewEnrollMemberByActivityCode(pager, activityCode, orderChannelSourceCode, isTop);
	}
	
	/**
	 * 通过活动code和会员code获取报名信息
	 * @param activityCode
	 * @param memberCode
	 * @return
	 */
	public List<MemberEnrollDetailVO> getMemberEnrollDetailByActCodeMemberCode(String activityCode, String memberCode){
		return memberEnrollDetailDao.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
	}
	
	/**
	 * 通过活动code和会员code 删除报名信息
	 * @param activityCode
	 * @param memberCode
	 */
	public void deleteMemberEnrollDetailByActCodeMemberCode(String activityCode, String memberCode){
	    Order order = orderDao.getOrderByActCodeMemberCode(activityCode, memberCode);
	    //删除订单
	    if(null!=order){
	    	orderDao.deleteLogicalByCode(order.getCode());
	    }
	    //删除报名信息
		memberEnrollDetailDao.deleteMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
	}
	
	/**
	 * 通过活动code和会员code 置顶
	 * @param activityCode
	 * @param memberCode
	 */
	public void updateMemberEnrollDetailIsTop(String activityCode, String memberCode, int isTop){
		memberEnrollDetailDao.updateMemberEnrollDetailIsTop(activityCode, memberCode, isTop);
	}

	@Override
	public MemberEnrollDetail getMemberEnrollDetailByActCodePropertyCodeMemberCode(String activityCode, String propertyCode, String memberCode) {
	    return memberEnrollDetailDao.getMemberEnrollDetailByActCodePropertyCodeMemberCode(activityCode, propertyCode, memberCode);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IMemberEnrollDetailDao getMemberEnrollDetailDao() {
		return memberEnrollDetailDao;
	}

	public void setMemberEnrollDetailDao(
			IMemberEnrollDetailDao memberEnrollDetailDao) {
		this.memberEnrollDetailDao = memberEnrollDetailDao;
	}

	
}

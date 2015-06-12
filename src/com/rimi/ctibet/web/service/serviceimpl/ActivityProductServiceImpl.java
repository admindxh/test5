package com.rimi.ctibet.web.service.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ActivityProduct;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.dao.IActivityDao;
import com.rimi.ctibet.web.dao.IActivityProductDao;
import com.rimi.ctibet.web.dao.IUserFavoriteDao;
import com.rimi.ctibet.web.service.IActivityProductService;
@Transactional
@Service("activityProductService")
public class ActivityProductServiceImpl extends BaseServiceImpl<ActivityProduct> implements IActivityProductService {

	@Resource IActivityProductDao activityProductDao;
	@Resource IActivityDao activityDao;
	@Resource IUserFavoriteDao userFavoriteDao;
	

	/**
	 * 获取参赛作品列表
	 * @param activityCode 活动code
	 * @param isTop	是否按置顶排序
	 * @param state	审核状态
	 * @return
	 */
	public Pager getActivityProductsByActCodeIsTop(Pager pager, String activityCode, boolean isTop, int state){
		return activityProductDao.getActivityProductsByActCodeIsTop(pager, activityCode, isTop, state);
	}
	
	/**
	 * 保存作品信息
	 * @param activityCode
	 * @param activityProduct
	 */
	public void saveActivityProduct(ActivityProduct activityProduct){
	    String bad = KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(activityProduct.getName(), "**");
        activityProduct.setName(bad);
		//活动上传数+1
		activityDao.updateActivityNumByColum(activityProduct.getActivityCode(), new String[]{"uploadNum"});
		activityProductDao.save(activityProduct);
		//保存参加信息
		UserFavorite userFavorite = userFavoriteDao.getByCode(activityProduct.getActivityCode(), activityProduct.getMemberCode());
		if(userFavorite==null){
		    userFavorite = new UserFavorite();
		    userFavorite.setMemberCode(activityProduct.getMemberCode());
		    userFavorite.setCode(activityProduct.getActivityCode());
		    userFavorite.setState("1");
		    userFavorite.setType(UserFavorite.User_Fav_Activity);
		    userFavorite.setJoinTime(DateUtil.getCurrentDate());
		    userFavoriteDao.save(userFavorite);
		}
	}
	
	/**
	 * 修改审核状态
	 * @param code
	 */
	public void updateState(String code, int state){
		ActivityProduct activityProduct = findByCode(code);
		activityProduct.setState(state);
		update(activityProduct);
	}
	
	/**
	 * 修改置顶字段
	 * @param code
	 */
	public void updateIsTop(String code, int isTop){
		ActivityProduct activityProduct = findByCode(code);
		activityProduct.setIsTop(isTop);
		update(activityProduct);
	}
	
	/**
	 * 修改伪赞字段
	 * @param code
	 */
	public void updateFakeLikeNum(String code, int fakeLikeNum){
		ActivityProduct activityProduct = findByCode(code);
		activityProduct.setFakeLikeNum(fakeLikeNum);
		update(activityProduct);
	}
	
	/**
	 * 点赞 +1
	 * @param code
	 */
	public void updateLikeNum(String code){
		ActivityProduct activityProduct = findByCode(code);
		activityProduct.setLikeNum(activityProduct.getLikeNum()+1);
		activityProduct.setFakeLikeNum(activityProduct.getFakeLikeNum()+1);
		update(activityProduct);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IActivityProductDao getActivityProductDao() {
		return activityProductDao;
	}

	public void setActivityProductDao(IActivityProductDao activityProductDao) {
		this.activityProductDao = activityProductDao;
	}
	
}

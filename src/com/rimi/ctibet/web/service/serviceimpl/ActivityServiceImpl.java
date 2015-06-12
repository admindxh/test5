package com.rimi.ctibet.web.service.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.VoteOption;
import com.rimi.ctibet.web.controller.vo.ActivetyTotalVo;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.dao.IActivityDao;
import com.rimi.ctibet.web.dao.IEnrollFormDao;
import com.rimi.ctibet.web.dao.IEnrollNoticeDao;
import com.rimi.ctibet.web.dao.IVoteOptionDao;
import com.rimi.ctibet.web.dao.ImageDao;
import com.rimi.ctibet.web.dao.MutiImageDao;
import com.rimi.ctibet.web.service.IActivityService;
@Transactional
@Service("activityService")
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements IActivityService {
	@Resource IActivityDao activityDao;
	@Resource IVoteOptionDao voteOptionDao;
	@Resource IEnrollNoticeDao enrollNoticeDao;
	@Resource IEnrollFormDao enrollFormDao;
	@Resource MutiImageDao mutiImageDao;
	@Resource ImageDao imageDao;

	/** 保存活动 */
	public void saveActivity(Activity activity, EnrollNotice enrollNotice, String options[], List<EnrollForm> listEnrollForm, List<Image> listImage){
		String activityCode = activity.getCode();
		
		//保存活动报名须知
		enrollNotice.setActivityCode(activityCode);
		enrollNotice.setCode(Uuid.uuid());
		if(activity.getIsEnroll()==0){
		    enrollNotice.setName1("");
		    enrollNotice.setName2("");
		    enrollNotice.setName3("");
		    enrollNotice.setSummary1("");
		    enrollNotice.setSummary2("");
		    enrollNotice.setSummary3("");
		}
		enrollNoticeDao.save(enrollNotice);
		
		//保存活动投票选项
		if(activity.getIsVote()==0){
            activity.setVoteName("");
            activity.setVoteOptionNum(0);
            activity.setVoteSummary("");
        }
		if(activity.getIsVote()==1 && options!=null && options.length>0){
			int i = 0;
			for (String option : options) {
				if(option!=null && !option.equals("")){
					VoteOption voteOption = new VoteOption(0, Uuid.uuid(), option, 0, 0, activityCode);
					voteOptionDao.save(voteOption);
					i++;
				}
			}
			//选项总数
			activity.setVoteOptionNum(i);
		}
		
		if(activity.getIsEnrollPay()==0){
            activity.setMoney(0);
        }
		//保存报名表单
		if(activity.getIsEnroll()==1 && ListUtil.isNotNull(listEnrollForm)){
			for (EnrollForm enrollForm : listEnrollForm) {
				enrollForm.setPropertyOptions(StringUtil.join(enrollForm.getPropertyOptions()));
				enrollForm.setCode(Uuid.uuid());
				enrollForm.setActivityCode(activityCode);
				enrollFormDao.save(enrollForm);
			}
		}
		
		//保存其他模块(图集相关表保存)
		if(ListUtil.isNotNull(listImage)){
			MutiImage mutiImage = new MutiImage();
			mutiImage.setAvaliable(1);
			mutiImage.setCode(Uuid.uuid());
			mutiImage.setProgramaCode(activityCode);	//活动code
			mutiImage.setName(activity.getOtherBlock());//其他模块名称
			mutiImage.setSummary(Activity.ACTIVITY_OTHERBLOCK);//表明是活动相关图片
			mutiImage.setCreateTime(new Timestamp(System.currentTimeMillis()));
			mutiImageDao.save(mutiImage);
			for (Image image : listImage) {
				image.setMutiCode(mutiImage.getCode());
				image.setAvaliable(1);
				image.setCode(Uuid.uuid());
				image.setCreateTime(new Timestamp(System.currentTimeMillis()));
				imageDao.save(image);
			}
		}
		
		//保存活动基础信息
		activity.setAvaliable(1);
		activity.setCreateTime(new Date(System.currentTimeMillis()));
		activityDao.save(activity);
	}
	
	/** 修改活动 */
	public void updateActivity(Activity activity, EnrollNotice enrollNotice, List<VoteOption> listVoteOption, List<EnrollForm> listEnrollForm, List<Image> listImage){
		String activityCode = activity.getCode();
		//保存这个新对象
		Activity newActivity = activityDao.findByCode(activityCode);
		//修改活动报名须知
		EnrollNotice OldEnrollNotice = enrollNoticeDao.getEnrollNoticesByActivityCode(activityCode);
		enrollNotice.setActivityCode(activityCode);
        if(activity.getIsEnroll()==0){
            enrollNotice.setName1("");
            enrollNotice.setName2("");
            enrollNotice.setName3("");
            enrollNotice.setSummary1("");
            enrollNotice.setSummary2("");
            enrollNotice.setSummary3("");
        }
		if(OldEnrollNotice!=null){
		    enrollNotice.setId(OldEnrollNotice.getId());
		    enrollNoticeDao.update(enrollNotice);
		}else{
		    enrollNoticeDao.save(enrollNotice);
		}
		
		//获取修改之前的所有选项
		List<VoteOption> beforeEditVoteOptionList = voteOptionDao.getVoteOptionsByActivityCode(activityCode);
		//保存活动投票选项
		if(activity.getIsVote()==0){
		    activity.setVoteName("");
		    activity.setVoteOptionNum(0);
		    activity.setVoteSummary("");
		}
		if(activity.getIsVote()==1 && ListUtil.isNotNull(listVoteOption)){
			
			//判断选项是否存在不存在就删除
			if(ListUtil.isNotNull(beforeEditVoteOptionList)){
				for (VoteOption beforeVote : beforeEditVoteOptionList) {
					boolean bool = false;//判断code是否相等，true相等更新，false不相等删除
					for (VoteOption vote : listVoteOption) {
						if(beforeVote.getCode().equals(vote.getCode())){
							beforeVote.setOptions(vote.getOptions());
							voteOptionDao.update(beforeVote);
							bool = true;
						}else if(!StringUtil.isNotNull(vote.getCode())){
							vote.setCode(Uuid.uuid());
							vote.setActivityCode(activityCode);
							voteOptionDao.save(vote);
						}
					}
					if(!bool){
						voteOptionDao.delete(beforeVote);
					}
				}
			}else{
			    if(ListUtil.isNotNull(listVoteOption)){
			        for (VoteOption vote : listVoteOption) {
			            vote.setCode(Uuid.uuid());
			            vote.setActivityCode(activityCode);
			            voteOptionDao.save(vote);
                    }
			    }
			}
			
			//选项总数
			newActivity.setVoteOptionNum(listVoteOption.size());
		}else if(ListUtil.isNotNull(beforeEditVoteOptionList)){
			for (VoteOption voteOption : beforeEditVoteOptionList) {
				voteOptionDao.delete(voteOption);
			}
		}
		
		if(activity.getIsEnrollPay()==0){
            activity.setMoney(0);
        }
		//获取修改前的所有报名表单 删除掉{
		if(activity.getIsEnroll()==1 && ListUtil.isNotNull(listEnrollForm)){
    		List<EnrollForm> beforeEditEnrollFormList = enrollFormDao.getEnrollFormByActivityCode(activityCode);
    		if(ListUtil.isNotNull(beforeEditEnrollFormList)){
    		    for (EnrollForm beforeEnrollForm : beforeEditEnrollFormList) {
    		        boolean bool = false;
                    for (EnrollForm enrollForm : listEnrollForm) {
                        if(beforeEnrollForm.getCode().equals(enrollForm.getCode())){
                            beforeEnrollForm.setPropertyOptions(StringUtil.join(enrollForm.getPropertyOptions()));
                            beforeEnrollForm.setProperty(enrollForm.getProperty());
                            beforeEnrollForm.setPropertyType(enrollForm.getPropertyType());
                            beforeEnrollForm.setPropertyTypeName(enrollForm.getPropertyTypeName());
                            beforeEnrollForm.setIsRequire(enrollForm.getIsRequire());
                            beforeEnrollForm.setSortNum(enrollForm.getSortNum());
                            enrollFormDao.update(beforeEnrollForm);
                            bool = true;
                        }else if(!StringUtil.isNotNull(enrollForm.getCode())){
                            enrollForm.setPropertyOptions(StringUtil.join(enrollForm.getPropertyOptions()));
                            enrollForm.setCode(Uuid.uuid());
                            enrollForm.setActivityCode(activityCode);
                            enrollFormDao.save(enrollForm);
                        }
                    }
                    if(!bool){
                        enrollFormDao.delete(beforeEnrollForm);
                    }
                }
    		    
    		}else if(ListUtil.isNotNull(listEnrollForm)){
    		    for (EnrollForm enrollForm : listEnrollForm) {
                    enrollForm.setPropertyOptions(StringUtil.join(enrollForm.getPropertyOptions()));
                    enrollForm.setCode(Uuid.uuid());
                    enrollForm.setActivityCode(activityCode);
                    enrollFormDao.save(enrollForm);
                }
    		}
		}
		//--
		
		
		/*if(ListUtil.isNotNull(beforeEditEnrollFormList)){
		    for (EnrollForm enrollForm : beforeEditEnrollFormList) {
		        enrollFormDao.deleteLogicalByCode(enrollForm.getCode());
		    }
		}
		if(activity.getIsEnroll()==1 && ListUtil.isNotNull(listEnrollForm)){
			//保存新报名表单
			for (EnrollForm enrollForm : listEnrollForm) {
			    enrollForm.setPropertyOptions(StringUtil.join(enrollForm.getPropertyOptions()));
				enrollForm.setCode(Uuid.uuid());
				enrollForm.setActivityCode(activityCode);
				enrollFormDao.save(enrollForm);
			}
		}*/
		//}
		
		//保存其他模块(图集相关表保存)
		if(ListUtil.isNotNull(listImage)){
			MutiImage mutiImage = mutiImageDao.getMutiImageByActCode(activityCode);
			if(mutiImage!=null){
				mutiImage.setLastEditTime(new Timestamp(System.currentTimeMillis()));
				mutiImageDao.update(mutiImage);
				//删除旧数据
				imageDao.deleteImageByMutiCode(mutiImage.getCode());
			}else{
				mutiImage = new MutiImage();
				mutiImage.setAvaliable(1);
				mutiImage.setCode(Uuid.uuid());
				mutiImage.setProgramaCode(activityCode);	//活动code
				mutiImage.setName(activity.getOtherBlock());//其他模块名称
				mutiImage.setSummary(Activity.ACTIVITY_OTHERBLOCK);//表明是活动相关图片
				mutiImage.setCreateTime(new Timestamp(System.currentTimeMillis()));
				mutiImageDao.save(mutiImage);
			}
			for (Image image : listImage) {
				image.setMutiCode(mutiImage.getCode());
				image.setAvaliable(1);
				image.setCode(Uuid.uuid());
				image.setCreateTime(new Timestamp(System.currentTimeMillis()));
				imageDao.save(image);
			}
		}
		
		//修改活动基本信息
		newActivity.setIsUpload(activity.getIsUpload());
		newActivity.setIsVote(activity.getIsVote());
		newActivity.setIsPay(activity.getIsPay());
		newActivity.setIsEnroll(activity.getIsEnroll());
		newActivity.setIsEnrollPay(activity.getIsEnrollPay());
		newActivity.setName(activity.getName());
		newActivity.setSortNum(activity.getSortNum());
		newActivity.setSummary(activity.getSummary());
		newActivity.setBannerImg(activity.getBannerImg());
		newActivity.setStartDate(activity.getStartDate());
		newActivity.setEndDate(activity.getEndDate());
		newActivity.setVoteName(activity.getVoteName());
		newActivity.setVoteSummary(activity.getVoteSummary());
		newActivity.setKeywords(activity.getKeywords());
		newActivity.setOtherBlock(activity.getOtherBlock());
		newActivity.setMoney(activity.getMoney());
		newActivity.setImg(activity.getImg());
		newActivity.setDescription(activity.getDescription());
		newActivity.setLinkUrl(activity.getLinkUrl());
		newActivity.setTdkTitle(activity.getTdkTitle());
		newActivity.setTdkDescription(activity.getTdkDescription());
		newActivity.setTdkKeywords(activity.getTdkKeywords());
		activityDao.updateLogical(newActivity);
		
		WebSearch webSearch = new WebSearch();
        webSearch.setCode(activity.getCode());
        webSearch.setUrl(newActivity.getLinkUrl());
        webSearch.setImageUrl(activity.getImg());
        webSearch.setTitle(activity.getName());
        webSearch.setContent(StringUtil.cleanHTML(activity.getSummary()));
        LuceneUtil2.update(webSearch);
		
	}
	
	/**
	 * 搜索活动信息管理列表
	 * @param name 活动名
	 * @param block	包含模块（上传、报名、投票、报名支付、支付 ）
	 * @param isEnd
	 * @param orderby
	 * @return
	 */
	public Pager searchActivityByName(Pager pager, String name, int block, int isEnd, int orderby){
		return activityDao.searchActivityByName(pager, name, block, isEnd, orderby);
	}
	
	/**
	 * 删除活动
	 * @param code
	 */
	public void deleteActivity(String code){
		activityDao.deleteLogicalByCode(code);
	}
	
	/**
	 * 批量删除活动
	 * @param code
	 */
	public void deleteActivity(String[] codes){
		for (String code : codes) {
			deleteActivity(code);
			WebSearch webSearch = new WebSearch();
	        webSearch.setCode(code);
	        LuceneUtil2.delete(webSearch);
		}
	}
	
	/**
	 * 按指定排序方式获取数据
	 * @param orderby
	 * @return
	 */
	public Pager getAllActivityOrder(Pager pager, int orderby, boolean isShowNotBegin, boolean isShowNotOffical){
		return activityDao.getAllActivityOrder(pager, orderby, isShowNotBegin, isShowNotOffical);
	}
	
	/**
	 * 通过活动code获取活动信息
	 * @param code
	 * @return
	 */
	public Activity getActivityByCode(String code){
		return activityDao.getActivityByCode(code);
	}
	
	/**
	 * 活动查看数+1
	 * @param activityCode
	 */
	public void activityCheckNumPlusOne(String activityCode){
		activityDao.activityCheckNumPlusOne(activityCode);
	}
	
	/**
	 * 指定字段 数量+1
	 * @param columns（字段，可以多个）
	 */
	public void updateActivityNumByColum(String activityCode, String[] columns){
		activityDao.updateActivityNumByColum(activityCode, columns);
	}
	
	/**
	 * 更新活动伪查看数和伪投票数
	 * @param activity
	 * @param listVoteOption
	 */
	public void updateActivityCheckNumVoteNum(Activity activity, List<VoteOption> listVoteOption){
		// 更新活动伪查看数
		activityDao.update(activity);
		if(ListUtil.isNotNull(listVoteOption)){
    		for (VoteOption voteOption : listVoteOption) {
    			String code = voteOption.getCode();
    			int fakeCounts = voteOption.getFakeCounts();
    			// 更新伪投票数
    			voteOptionDao.updateFakeCountsByCode(code, fakeCounts);
    		}
		}
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(IActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public IVoteOptionDao getVoteOptionDao() {
		return voteOptionDao;
	}

	public void setVoteOptionDao(IVoteOptionDao voteOptionDao) {
		this.voteOptionDao = voteOptionDao;
	}

	@Override
	public Pager findPagerBySQL(String sql, List params, Pager pager) {
		// TODO Auto-generated method stub
		return activityDao.findListPagerBySql(Activity.class, pager, sql, params);
	}

	//main
	public static void main(String[] args) {
		String propertyOptions = "asd,四大，发的 ， 吖 ，  ,asd,dd ，";
		String[] split = propertyOptions.trim().replace("，",",").split(",");
		List<String> list = new ArrayList<String>();
		for (String s : split) {
			String trim = s.trim();
			if(StringUtil.isNotNull(trim)){
				list.add(trim);
			}
		}
		//System.out.println(split);
		//System.out.println(list);
		//System.out.println(ListUtil.join(list.toArray(new String[list.size()]), ","));
		//System.out.println(StringUtil.join(propertyOptions));
	}

	@Override
	public Pager findPagerByTime(Pager pager,String start, String end) {
		
		return null;
	}
	/**
	 * 查询总的统计
	 * @param start
	 * @param end
	 * @return
	 */
	public  ActivetyTotalVo  getActivetyTotal(String start,String end){
		return activityDao.getActivetyTotal(start, end);
	}
	
	public  Pager  getActivetyTotalPager(String start,String end,Pager pager){
		return activityDao.getActivetyTotalPager(start, end, pager);
	}

    @Override
    public List<MemberEnrollDetailVO> getJoinMemberList(int row) {
        return activityDao.getJoinMemberList(row);
    }

    @Override
    public int getMaxNum() {
        return activityDao.getMaxNum();
    }
	
	
}

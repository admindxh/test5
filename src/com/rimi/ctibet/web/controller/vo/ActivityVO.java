package com.rimi.ctibet.web.controller.vo;

import java.util.Date;
import java.util.List;

import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.domain.VoteOption;

public class ActivityVO extends Activity {
	
	List<Activity> listActivity;
	List<EnrollForm> listEnrollForm;
	List<MemberEnrollDetail> listMemberEnrollDetail;
	List<EnrollNotice> listEnrollNotice;
	List<VoteOption> listVoteOption;
	List<Image> listImage;
	
	// 会员code
	private String memberCode;
	// 会员name
	private String memberName;
	// 报名时间
	private Date enrollTime;
	
	// 活动进行状态
	private String activityState;
	// 拼接包含的模块名字
	private String blocks;
	public String getBlocks() {
		blocks = "";
		if(getIsUpload()==1){
			blocks += BLOCK_MAP.get(isUploadName) + "、";
		}
		if(getIsVote()==1){
			blocks += BLOCK_MAP.get(isVoteName) + "、";
		}
		if(getIsEnroll()==1){
			blocks += BLOCK_MAP.get(isEnrollName) + "、";
		}
		if(getIsEnrollPay()==1){
			blocks += BLOCK_MAP.get(isEnrollPayName) + "、";
		}
		if(getIsPay()==1){
			blocks += BLOCK_MAP.get(isPayName) + "、";
		}
		if(blocks.lastIndexOf("、")!=-1){
		    blocks = blocks.substring(0, blocks.lastIndexOf("、"));
        }
		return blocks;
	}
	/********************************************
	 * Setter Getter
	 ********************************************/
	public void setBlocks(String blocks) {
		this.blocks = blocks;
	}
	public List<Activity> getListActivity() {
		return listActivity;
	}
	public void setListActivity(List<Activity> listActivity) {
		this.listActivity = listActivity;
	}
	public List<EnrollNotice> getListEnrollNotice() {
		return listEnrollNotice;
	}
	public void setListEnrollNotice(List<EnrollNotice> listEnrollNotice) {
		this.listEnrollNotice = listEnrollNotice;
	}
	public List<EnrollForm> getListEnrollForm() {
		return listEnrollForm;
	}
	public void setListEnrollForm(List<EnrollForm> listEnrollForm) {
		this.listEnrollForm = listEnrollForm;
	}
	public List<VoteOption> getListVoteOption() {
		return listVoteOption;
	}
	public void setListVoteOption(List<VoteOption> listVoteOption) {
		this.listVoteOption = listVoteOption;
	}
	public List<Image> getListImage() {
		return listImage;
	}
	public void setListImage(List<Image> listImage) {
		this.listImage = listImage;
	}
	public String getActivityState() {
		return activityState;
	}
	public void setActivityState(String activityState) {
		this.activityState = activityState;
	}
	public List<MemberEnrollDetail> getListMemberEnrollDetail() {
		return listMemberEnrollDetail;
	}
	public void setListMemberEnrollDetail(
			List<MemberEnrollDetail> listMemberEnrollDetail) {
		this.listMemberEnrollDetail = listMemberEnrollDetail;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Date getEnrollTime() {
		return enrollTime;
	}
	public void setEnrollTime(Date enrollTime) {
		this.enrollTime = enrollTime;
	}
	
	public static void main(String[] args) {
        String s = "adasd、";
        if(s.lastIndexOf("、")!=-1){
            String string = s.substring(0, s.lastIndexOf("、"));
            //System.out.println(string);
        }
        //System.out.println(s.lastIndexOf("、"));
        
    }
	
}

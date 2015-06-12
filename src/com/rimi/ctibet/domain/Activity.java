package com.rimi.ctibet.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rimi.ctibet.common.domain.BaseDomain;

/**
 *	活动
 */
public class Activity extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 活动关联表                           
	 * activity   活动表                  
	 * enroll_form    报名表单             
	 * member_enroll_detail   存放会员报名资料 
	 * enroll_notice  报名须知             
	 * activity_product   存放会员作品       
	 * vote_option    存放投票选项           
	 */
	
	public static final String ACTIVITY_OTHERBLOCK="activity_otherblock";
	
	public static final Map<Integer, String> BLOCK_MAP = getBlockMap();
	public static final int isAll = 0;//全部
	public static final int isUploadName = 1;//上传
	public static final int isVoteName = 2;//投票
	public static final int isEnrollName = 3;//报名
	public static final int isEnrollPayName = 4;//报名支付
	public static final int isPayName = 5;//支付
	
	public static final int ACTIVITYDATE_ALL = 1;//全部
	public static final int ACTIVITYDATE_START = 2;//活动进行中
	public static final int ACTIVITYDATE_END = 3;//活动结束
	public static final int ACTIVITYDATE_NOT_START = 4;//活动进行中
	
	public static final int ORDERBY_DATE = 1;//排序日期
	public static final int ORDERBY_ENROLLNUM = 2;//排序报名数
	public static final int ORDERBY_UPLOADNUM = 3;//排序上传数
	public static final int ORDERBY_CHECKNUM = 4;//排序查看数
	public static final int ORDERBY_FAVORITE = 5;//排序收藏数
	public static final int ORDERBY_REPLY = 6;//排序评论数
	
	//public static final String LINK_URL_ACTIVITY_DETAIL = "activity/forActivityDetail";//活动详情页地址
	public static final String LINK_URL_ACTIVITY_DETAIL = "activity/detail";//活动详情页地址
	//public static final String LINK_URL_SPECIAL_DETAIL = "special/forSpecialDetail";//专题详情页地址
	public static final String LINK_URL_SPECIAL_DETAIL = "special/detail";//专题详情页地址
	
	//后台活动banner管理mutiCode
	public static final String MUTI_ACTIVITY_BANNER_CODE = "4edbde04-8595-11e4-b9e4-005056a05bc9";
	//后台专题显示管理mutiCode
	public static final String MUTI_SPECAIL_SHOW_CODE = "6a8226cb-8595-11e4-b9e4-005056a05bc9";
	
	
	// 是否上传 1是 0否
    private int isUpload;
    // 是否投票 1是 0否
    private int isVote;
    // 是否报名 1是 0否
    private int isEnroll;
    // 是否报名支付 1是 0否
    private int isEnrollPay;
    // 是否支付 1是 0否
    private int isPay;
    // 报名费
    private float money;
    // 序号
    private int sortNum;
    // 活动名
    private String name;
    // 活动简介
    private String summary;
    // 缩略图
    private String img;
    // banner图
    private String bannerImg;
    // 活动开始时间
    private Date startDate;
    // 活动结束时间
    private Date endDate;
    // 投票名称
    private String voteName;
    // 投票简介
    private String voteSummary;
    // 投票选项数量
    private int voteOptionNum;
    // 其他模块名称
    private String otherBlock;
    // 报名数
    private int enrollNum;
    // 上传作品数
    private int uploadNum;
    // 查看数
    private int checkNum;
    // 伪查看数
    private int fakeCheckNum;
    // linkUrl
    private String linkUrl;

    // SEO
    private String title;
    private String description;
    private String keywords;
	
    public Activity() {}

	@Override
    public String toString() {
        return "Activity [isUpload=" + isUpload + ", isVote=" + isVote
                + ", isEnroll=" + isEnroll + ", isEnrollPay=" + isEnrollPay
                + ", isPay=" + isPay + ", money=" + money + ", sortNum="
                + sortNum + ", name=" + name + ", summary=" + summary
                + ", img=" + img + ", bannerImg=" + bannerImg + ", startDate="
                + startDate + ", endDate=" + endDate + ", voteName=" + voteName
                + ", voteSummary=" + voteSummary + ", voteOptionNum="
                + voteOptionNum + ", otherBlock=" + otherBlock + ", enrollNum="
                + enrollNum + ", uploadNum=" + uploadNum + ", checkNum="
                + checkNum + ", fakeCheckNum=" + fakeCheckNum + ", linkUrl="
                + linkUrl + ", title=" + title + ", description=" + description
                + ", keywords=" + keywords + ", toString()=" + super.toString()
                + "]";
    }
	
	/**
	 * 模块
	 * @return
	 */
	private static Map<Integer, String> getBlockMap(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(isAll, "全部模块");
		map.put(isUploadName, "上传");
		map.put(isVoteName, "投票");
		map.put(isEnrollName, "报名");
		map.put(isEnrollPayName, "报名支付");
		map.put(isPayName, "支付");
		return map;
	}

	/********************************************
	 * Setter Getter
	 ********************************************/
	
	public int getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(int isUpload) {
		this.isUpload = isUpload;
	}

	public int getIsVote() {
		return isVote;
	}

	public void setIsVote(int isVote) {
		this.isVote = isVote;
	}

	public int getIsEnroll() {
		return isEnroll;
	}

	public void setIsEnroll(int isEnroll) {
		this.isEnroll = isEnroll;
	}

	public int getIsEnrollPay() {
		return isEnrollPay;
	}

	public void setIsEnrollPay(int isEnrollPay) {
		this.isEnrollPay = isEnrollPay;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getVoteName() {
		return voteName;
	}

	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}

	public String getVoteSummary() {
		return voteSummary;
	}

	public void setVoteSummary(String voteSummary) {
		this.voteSummary = voteSummary;
	}

	public String getOtherBlock() {
		return otherBlock;
	}

	public void setOtherBlock(String otherBlock) {
		this.otherBlock = otherBlock;
	}

	public int getEnrollNum() {
		return enrollNum;
	}

	public void setEnrollNum(int enrollNum) {
		this.enrollNum = enrollNum;
	}

	public int getUploadNum() {
		return uploadNum;
	}

	public void setUploadNum(int uploadNum) {
		this.uploadNum = uploadNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getVoteOptionNum() {
		return voteOptionNum;
	}

	public void setVoteOptionNum(int voteOptionNum) {
		this.voteOptionNum = voteOptionNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getFakeCheckNum() {
		return fakeCheckNum;
	}

	public void setFakeCheckNum(int fakeCheckNum) {
		this.fakeCheckNum = fakeCheckNum;
	}

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}

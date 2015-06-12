
package com.rimi.ctibet.domain;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Transient;

import com.rimi.ctibet.common.domain.BaseDomain;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.StringUtil;

public class Content extends BaseDomain {
	private static final long serialVersionUID = 1L;
	/** 攻略 */
	public static final String CONTENTTYPE_STRATEGY = "strategy";
	/** 帖子 */
	public static final String CONTENTTYPE_POST = "post";
	/** 回复 */
	public static final String CONTENTTYPE_REPLY = "reply";
	/** 视频 */
	public static final String CONTENTTYPE_VEDIO = "vedio";
	/** 动态 */
	public static final String CONTENTTYPE_DYNAMIC = "dynamic";
	/** 活动 */
	public static final String CONTENTTYPE_ACTIVITY = "activity";
	/** 专题 */
	public static final String CONTENTTYPE_SPECIAL = "special";
	/** 商 户*/
	public static String CONTENTTYPE_MERCHANT = "merchant";
	
	/** 其他页面 */
	public static final String CONTENTTYPE_OTHER = "4010";
	/** 其他默认页面*/
	public static final String CONTENTTYPE_OTHER_DEFAULT = "4020";
	
	
	// 前台详情页评论类型 contentType {
    /** 读西藏详情评论 */
    public static final String DETAIL_READ_TIBET_REPLY = "read_tibet_reply";
    /** 读西藏文化详情评论 */
    public static final String DETAIL_READ_TIBET_CULTURE_REPLY = "read_tibet_culture_reply";
    /** 景点详情评论 */
    public static final String DETAIL_VIEW_REPLY = "view_reply";
    /** 攻略详情评论 */
    public static final String DETAIL_STRATEGY_REPLY = "strategy_reply";
    /** 商户详情评论 */
    public static final String DETAIL_MERCHANT_REPLY = "merchant_reply";
    /** 团游详情评论 */
    public static final String DETAIL_TOUR_GROUP_REPLY = "tour_group_reply";
    /** 看西藏 视频 详情评论 */
    public static final String DETAIL_VEDIO_REPLY = "vedio_reply";
    /** 看西藏 图说 详情评论 */
    public static final String DETAIL_PICTURE_REPLY = "picture_reply";
	//}
    
    //审核状态{
    /** 已审核 */
    public static final int REVIEW_STATE_PASS = 1;
    /** 待审核 */
    public static final int REVIEW_STATE_WAIT = 0;
    /** 未通过 */
    public static final int REVIEW_STATE_NOT_PASS = -1;
    /** 全部 */
    public static final int REVIEW_STATE_ALL = 100;
    //}
    
    
    //骑行社区板块code{
    /** 骑行公告 */
    public static final String RIDE_GG = "409fb234-8639-452a-a440-88c210f5ce6f";
    /** 骑行故事 */
    public static final String RIDE_GS = "1e7e7437-5858-42a7-bd15-1d29f93326f0";
    /** 骑行征集 */
    public static final String RIDE_ZJ = "2a13c8e4-e657-44e9-a29c-c7d13b9f1085";
    /** 骑行装备 */
    public static final String RIDE_ZB = "d8e55a99-146b-43f1-be0c-9381b03f862f"; 
    //}
    
    //排序{
    /** 按时间倒序 */
    public static final int ORDER_CREATETIME_DESC = 1;
    /** 按回复数倒序 */
    public static final int ORDER_REPLYNUM_DESC = 2;
    /** 按赞数倒序 */
    public static final int ORDER_PRAISE_DESC = 3;
    //}
    
    /*********************************************/
	
	// 栏目code（目前只针对攻略和帖子）
	private String programaCode;
	// 类型
	private String contentType;
	// 标题
	private String contentTitle;
	// 作者(现直接存会员名字)
	private String authorCode;
	// 图片、视频的url
	private String url;
	// 内容
	private String content;
	//
	private String summary;
	// 标签
	private String tag;
	// 创建人code
	private String createuserCode;
	// 修改人code
	private String changeuserCode;
	// 审核状态
	private int state = 0;
	// 审核人code
	private String checkuserCode;
	// 官方标记
	private int isOfficial;
	private int sortNum;
	// 置顶，0否 1置顶
	private int isTop;
	
	//seo信息
	private String title;
	private String description;
	private String keywords;
	
	//其他字段信息{
	private Map others;
	private String contetNotHtml;
	@Transient
	private Date joinTime;
	private String postCode;
	private String postTitle;
	private String postUrl;
	//}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public Map getOthers() {
		return others;
	}
	public void setOthers(Map others) {
		this.others = others;
	}

	//帮助信息的获取
	private Integer  falsePraise;//赞
	private Integer   falseViewCount;//浏览数
	private Integer   falseFavite;//收藏数
	
	private String imgUrl;//获取图片攻略路径
	
	@Transient
	private String programaName;
	
	public String getProgramaName() {
		return programaName;
	}
	public void setProgramaName(String programaName) {
		this.programaName = programaName;
	}
	
	public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public String getPostUrl() {
        return postUrl;
    }
    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
    public Content() {}
	


	@Override
	public String toString() {
		return "Content [programaCode=" + programaCode + ", contentType="
				+ contentType + ", contentTitle=" + contentTitle
				+ ", authorCode=" + authorCode + ", url=" + url + ", content="
				+ content + ", summary=" + summary + ", tag=" + tag
				+ ", createuserCode=" + createuserCode + ", changeuserCode="
				+ changeuserCode + ", state=" + state + ", checkuserCode="
				+ checkuserCode + ", isOfficial=" + isOfficial + ", sortNum="
				+ sortNum + ", isTop=" + isTop + ", title=" + title
				+ ", description=" + description + ", keywords=" + keywords
				+ ", others=" + others + ", falsePraise=" + falsePraise
				+ ", falseViewCount=" + falseViewCount + ", falseFavite="
				+ falseFavite + ", imgUrl=" + imgUrl + ", contetNotHtml="
				+ contetNotHtml + "]";
	}

	public String getProgramaCode() {
		return programaCode;
	}
	public void setProgramaCode(String programaCode) {
		this.programaCode = programaCode;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getAuthorCode() {
		return authorCode;
	}
	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCreateuserCode() {
		return createuserCode;
	}
	public void setCreateuserCode(String createuserCode) {
		this.createuserCode = createuserCode;
	}
	public String getChangeuserCode() {
		return changeuserCode;
	}
	public void setChangeuserCode(String changeuserCode) {
		this.changeuserCode = changeuserCode;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCheckuserCode() {
		return checkuserCode;
	}
	public void setCheckuserCode(String checkuserCode) {
		this.checkuserCode = checkuserCode;
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
	public int getIsOfficial() {
		return isOfficial;
	}
	public void setIsOfficial(int isOfficial) {
		this.isOfficial = isOfficial;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public String getContetNotHtml(){
		if(StringUtil.isNotNull(content))
		{
			String msg=	content.replaceAll("<[^>]*>","").replaceAll("&nbsp;", "");
			  Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	          Matcher m = p.matcher(msg);
	          msg = m.replaceAll("");
			this.contetNotHtml=msg;
			return msg;
		}
		return content;  
	}
	
	public String getDateFormat(){
		return DateUtil.dateToStr(getCreateTime(), "yyyy/MM/dd");
	}
	public Integer getFalsePraise() {
		return falsePraise;
	}
	public void setFalsePraise(Integer falsePraise) {
		this.falsePraise = falsePraise;
	}
	public Integer getFalseViewCount() {
		return falseViewCount;
	}
	
	public void setFalseViewCount(Integer falseViewCount) {
		this.falseViewCount = falseViewCount;
	}
	
	public Integer getFalseFavite() {
		return falseFavite;
	}
	public void setFalseFavite(Integer falseFavite) {
		this.falseFavite = falseFavite;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public void setContetNotHtml(String contetNotHtml) {
		this.contetNotHtml = contetNotHtml;
	}
	
}

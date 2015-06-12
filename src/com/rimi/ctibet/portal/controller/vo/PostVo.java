package com.rimi.ctibet.portal.controller.vo;

import com.rimi.ctibet.domain.Content;

/**
 * @author xiaozhen
 * 封装post的所有信息，包括所有的回复内容，和回复人信息
 */
public class PostVo {

     private String title;
	 private String replyerName;
	 private String replyerPic;
	 private String replyTime;
	 private Integer praiseCount;
	 private String level;
	 private String content;
	 private String replyCode;
	 private String code;
	 
	 private Content newReply;
	 private int checkNum;
	 private int replyNum;
	 private int postNum;
	 private String summary;
	 private String url;
	 private String img;
	 private String progrmaName;
	 private String postCode;
     private String postTitle;
     private String postUrl;
	 //
	 
	 public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	public String getReplyerName() {
		return replyerName;
	}
	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName;
	}
	public String getReplyerPic() {
		return replyerPic;
	}
	public void setReplyerPic(String replyerPic) {
		this.replyerPic = replyerPic;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Content getNewReply() {
        return newReply;
    }
    public void setNewReply(Content newReply) {
        this.newReply = newReply;
    }
    public int getCheckNum() {
        return checkNum;
    }
    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }
    public int getReplyNum() {
        return replyNum;
    }
    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
    public int getPostNum() {
        return postNum;
    }
    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }
    
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    
    public String getProgrmaName() {
        return progrmaName;
    }
    public void setProgrmaName(String progrmaName) {
        this.progrmaName = progrmaName;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    @Override
    public String toString() {
        return "PostVo [title=" + title + ", replyerName=" + replyerName
                + ", replyerPic=" + replyerPic + ", replyTime=" + replyTime
                + ", praiseCount=" + praiseCount + ", level=" + level
                + ", content=" + content + ", replyCode=" + replyCode
                + ", code=" + code + ", newReply=" + newReply + ", checkNum="
                + checkNum + ", replyNum=" + replyNum + ", postNum=" + postNum
                + ", summary=" + summary + ", url=" + url + ", img=" + img
                + ", progrmaName=" + progrmaName + ", toString()="
                + super.toString() + "]";
    }
	
}

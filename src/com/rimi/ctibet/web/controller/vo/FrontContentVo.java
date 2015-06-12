package com.rimi.ctibet.web.controller.vo;

/**
 * 统计 帮助 vo
 * @author dengxh
 *
 */
/**
 * @author dengxh
 *
 */
public class FrontContentVo {
	
	private String  imgUrl;//图片路径
	private String merchantName;
	private String url;
	private String proName;//统计名称类型
	private Integer clickCount;//浏览数
	private Integer favateCount;//收藏数
	private Integer praiseCount;//赞数
	private Integer replyCount;//评论数
	private Integer hrefCount;//外链数
	
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
		
	
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getFavateCount() {
		return favateCount;
	}
	public void setFavateCount(Integer favateCount) {
		this.favateCount = favateCount;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	public Integer getHrefCount() {
		return hrefCount;
	}
	public void setHrefCount(Integer hrefCount) {
		this.hrefCount = hrefCount;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}

package com.rimi.ctibet.domain;

import java.sql.Timestamp;

import com.rimi.ctibet.common.util.StringUtil;

/**
 * 目的地类
 * 
 * @author xiangwq
 * 
 */
public class Destination {

	private int id;
	private String code;
	private String destinationName;
	private String destinationImage;
	private String destinationSummary;
	private String destinationIntroduce;
	private Timestamp createTime;
	private Timestamp lastEditTime;
	private Integer avaliable;
	private Integer isHaveAirTicket;
	private String jp;
	private String keyword;
	private String hyberlink;
	private String enName;
	private String tibetName;
	private int goneCount;
	private int wantCount;
	private int falseGoneCount;
	private int falseWantCount;
	private String linkUrl;

	private String jpName;
	
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Destination() {
	}

	public Destination(int id, String destinationName,
			String destinationImage, String destinationSummary,
			String destinationIntroduce, Timestamp createTime,
			Timestamp lastEditTime, Integer avaliable, Integer isHaveAirTicket,
			String jp,String keyword,String hyberlink,String enName,String tibetName) {
		super();
		this.id = id;
		this.destinationName = destinationName;
		this.destinationImage = destinationImage;
		this.destinationSummary = destinationSummary;
		this.destinationIntroduce = destinationIntroduce;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
		this.avaliable = avaliable;
		this.isHaveAirTicket = isHaveAirTicket;
		this.jp=jp;
        this.keyword=keyword;
        this.hyberlink=hyberlink;
        this.enName=enName;
        this.tibetName = tibetName;
	}

	



	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getJp() {
		return jp;
	}

	public void setJp(String jp) {
		this.jp = jp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationImage() {
		return destinationImage;
	}

	public void setDestinationImage(String destinationImage) {
		this.destinationImage = destinationImage;
	}

	public String getDestinationSummary() {
		return destinationSummary;
	}

	public void setDestinationSummary(String destinationSummary) {
		this.destinationSummary = destinationSummary;
	}

	public String getDestinationIntroduce() {
		return destinationIntroduce;
	}

	public void setDestinationIntroduce(String destinationIntroduce) {
		this.destinationIntroduce = destinationIntroduce;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Timestamp lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public Integer getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	}

	public Integer getIsHaveAirTicket() {
		return isHaveAirTicket;
	}

	public void setIsHaveAirTicket(Integer isHaveAirTicket) {
		this.isHaveAirTicket = isHaveAirTicket;
	}
 
	
	public String getHyberlink() {
		return hyberlink;
	}

	public void setHyberlink(String hyberlink) {
		this.hyberlink = hyberlink;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getTibetName() {
		return tibetName;
	}

	public void setTibetName(String tibetName) {
		this.tibetName = tibetName;
	}

	

	public int getGoneCount() {
		return goneCount;
	}

	public void setGoneCount(int goneCount) {
		this.goneCount = goneCount;
	}

	public int getWantCount() {
		return wantCount;
	}

	public void setWantCount(int wantCount) {
		this.wantCount = wantCount;
	}

	public int getFalseGoneCount() {
		return falseGoneCount;
	}

	public void setFalseGoneCount(int falseGoneCount) {
		this.falseGoneCount = falseGoneCount;
	}

	public int getFalseWantCount() {
		return falseWantCount;
	}

	public void setFalseWantCount(int falseWantCount) {
		this.falseWantCount = falseWantCount;
	}

	public String getJpName() {
	    if(!StringUtil.isNotNull(this.jp)){
	        this.jpName = "拉萨";
	    }else if(this.jp.equals("0")){
	        this.jpName = "拉萨";
        }else if(this.jp.equals("1")){
            this.jpName = "林芝";
        }else if(this.jp.equals("2")){
            this.jpName = "昌都";
        }else if(this.jp.equals("3")){
            this.jpName = "拉萨";
        }else if(this.jp.equals("4")){
            this.jpName = "林芝";
        }
        return jpName;
    }

    public void setJpName(String jpName) {
        this.jpName = jpName;
    }

    @Override
	public String toString() {
		return "Destination[avaliable=" + avaliable + ", createTime="
				+ createTime + ",lastEditTime=" + lastEditTime + ",avaliable="
				+ avaliable + ",id=" + id + ","
				+ ",destinationName=" + destinationName
				+ ",destinationSummary=" + destinationSummary
				+ ",destinationImage=" + destinationImage + ","
				+ "destinationIntroduce=" + destinationIntroduce + ",jp="+jp+", keyword = " + keyword + "]";
	}

}

package com.rimi.ctibet.domain;

import java.util.Date;

import javax.persistence.Transient;

/**
 * 商戶對象
 * */

public class Merchant {    
      private Integer  id;
      private String  merchantName;
      private String code;
	  private String  viewCode;
	  private String  merchantType;
	  //所属目的地
	  private String distination;
	  private Integer  isRecommend;
	  //商户图片
	  private String  merchantImage;
	  private String  merchantSummary;//地址
	  private String  merchantDetail;
	  private Date  createTime;
	  private Date  lastEditTime;
	  private Integer  avaliable = 1;
	  //帮助属性
	  private String help;
	  //携程网的路径。
	  private String ctripUrl;
	  //自身url
	  private String url;
	  private String keyWord;
	  private String activityUrl;
	  @Transient
	  private Date joinTime;
	  
	  
	  private String price;
      /**
       * tdk
       */
      private String tdkTitle;
      private String tdkDescription;
      private String tdkKeywords;
	  public Merchant(){}
	  
	  public Merchant(Integer id, String merchantName, String code,
			String viewCode, String merchantType, Integer isRecommend,
			String merchantImage, String merchantSummary,
			String merchantDetail, Date createTime, Date lastEditTime,
			Integer available) {
		super();
		this.id = id;
		this.merchantName = merchantName;
		this.code = code;
		this.viewCode = viewCode;
		this.merchantType = merchantType;
		this.isRecommend = isRecommend;
		this.merchantImage = merchantImage;
		this.merchantSummary = merchantSummary;
		this.merchantDetail = merchantDetail;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
		this.avaliable = available;
	}

       
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCtripUrl() {
		return ctripUrl;
	}

	public void setCtripUrl(String ctripUrl) {
		this.ctripUrl = ctripUrl;
	}

	public String getDistination() {
		return distination;
	}

	public void setDistination(String distination) {
		this.distination = distination;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getMerchantImage() {
		return merchantImage;
	}

	public void setMerchantImage(String merchantImage) {
		this.merchantImage = merchantImage;
	}

	public String getMerchantSummary() {
		return merchantSummary;
	}

	public void setMerchantSummary(String merchantSummary) {
		this.merchantSummary = merchantSummary;
	}

	public String getMerchantDetail() {
		return merchantDetail;
	}

	public void setMerchantDetail(String merchantDetail) {
		this.merchantDetail = merchantDetail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public Integer getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getTdkTitle() {
        return tdkTitle;
    }

    public void setTdkTitle(String tdkTitle) {
        this.tdkTitle = tdkTitle;
    }

    public String getTdkDescription() {
        return tdkDescription;
    }

    public void setTdkDescription(String tdkDescription) {
        this.tdkDescription = tdkDescription;
    }

    public String getTdkKeywords() {
        return tdkKeywords;
    }

    public void setTdkKeywords(String tdkKeywords) {
        this.tdkKeywords = tdkKeywords;
    }
	  
}

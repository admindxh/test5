package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * 骑行装备表
 * @author dengxh
 *
 */
public class Equipment {
	
	private Integer  id;//
	private String code ;
	private Integer avaliable;
	private String 	name;
	private String summary;//简介
	private String price;//价格
	
	private Integer count;//装备数量
	
	private String content;//内容
	
	private String remark;//备注
	private String proType ;//栏目类型
	
	private String recommoned;//是否推荐
	private String payType;//付费类型
	private String payurl;//付费地址
	
	private String imgmuticode;//就是当前code ，同时关联 image 表中的 ImageMultiCode 去查询图集
	
	private String sumaryimg;//简介图片地址
	
	private String smallimg;//缩略图图片
	
	private  Date createtime;
	
	private String url;//访问地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public Integer getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getRecommoned() {
		return recommoned;
	}

	public void setRecommoned(String recommoned) {
		this.recommoned = recommoned;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayurl() {
		return payurl;
	}

	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}

	public String getImgmuticode() {
		return imgmuticode;
	}

	public void setImgmuticode(String imgmuticode) {
		this.imgmuticode = imgmuticode;
	}

	public String getSumaryimg() {
		return sumaryimg;
	}

	public void setSumaryimg(String sumaryimg) {
		this.sumaryimg = sumaryimg;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSmallimg() {
		return smallimg;
	}

	public void setSmallimg(String smallimg) {
		this.smallimg = smallimg;
	}
	
	
	
}

package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * 服务站点
 * @author dengxh
 *
 */
public class ServiceSite {
		private Integer id;
		private String code;
		private Integer avaliable;
		private String siteName;
		private String sitImg;
		private String serviceName;
		private String serviceImg;
		private String serviceAdress;
		private String servicePhone;
		
		private String ridelineId;
		
		private String orderSite;//排序 1,2.。。
		
		private Date createTime;
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
		public String getSiteName() {
			return siteName;
		}
		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}
		public String getSitImg() {
			return sitImg;
		}
		public void setSitImg(String sitImg) {
			this.sitImg = sitImg;
		}
		public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		public String getServiceImg() {
			return serviceImg;
		}
		public void setServiceImg(String serviceImg) {
			this.serviceImg = serviceImg;
		}
		public String getServiceAdress() {
			return serviceAdress;
		}
		public void setServiceAdress(String serviceAdress) {
			this.serviceAdress = serviceAdress;
		}
		public String getServicePhone() {
			return servicePhone;
		}
		public void setServicePhone(String servicePhone) {
			this.servicePhone = servicePhone;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public String getRidelineId() {
			return ridelineId;
		}
		public void setRidelineId(String ridelineId) {
			this.ridelineId = ridelineId;
		}
		public String getOrderSite() {
			return orderSite;
		}
		public void setOrderSite(String orderSite) {
			this.orderSite = orderSite;
		}
		
		
		
	
}
	
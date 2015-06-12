package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * 推荐 站点内容 商户或者攻略
 * @author dengxh
 *
 */
public class RecommonedSite {
		private Integer id;
		private String code;
		private Integer avaliable;
		private Date createtime;
		private String conentCode;
		private String contentType;
		private String sitecode;
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
		public Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
		public String getConentCode() {
			return conentCode;
		}
		public void setConentCode(String conentCode) {
			this.conentCode = conentCode;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public String getSitecode() {
			return sitecode;
		}
		public void setSitecode(String sitecode) {
			this.sitecode = sitecode;
		}
		
		
}

package com.rimi.ctibet.domain;

import java.util.Date;

/**
 * pv 页面浏览量统计
 * @author dengxh
 *
 */
public class PageView {
		private Integer id;
		private String page;
		private Date viewtime;
		private String ip;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getPage() {
			return page;
		}
		public void setPage(String page) {
			this.page = page;
		}
		public Date getViewtime() {
			return viewtime;
		}
		public void setViewtime(Date viewtime) {
			this.viewtime = viewtime;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		
}

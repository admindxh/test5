package com.rimi.ctibet.portal.controller.vo;

import java.util.List;

import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.ServiceSite;

/**
 * 站点 vo
 * @author dengxh
 *
 */
public class RideLineVo {
	
		private ServiceSite serviceSite;

		private List<Merchant> merchantShu ;
		
		private List<Merchant> merchantShi ;
		
		private List<Content> content;

		public ServiceSite getServiceSite() {
			return serviceSite;
		}

		public void setServiceSite(ServiceSite serviceSite) {
			this.serviceSite = serviceSite;
		}

		public List<Merchant> getMerchantShu() {
			return merchantShu;
		}

		public void setMerchantShu(List<Merchant> merchantShu) {
			this.merchantShu = merchantShu;
		}

		public List<Merchant> getMerchantShi() {
			return merchantShi;
		}

		public void setMerchantShi(List<Merchant> merchantShi) {
			this.merchantShi = merchantShi;
		}

		public List<Content> getContent() {
			return content;
		}

		public void setContent(List<Content> content) {
			this.content = content;
		}
		
		
}

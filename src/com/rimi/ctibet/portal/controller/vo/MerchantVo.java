package com.rimi.ctibet.portal.controller.vo;

import java.util.List;

import com.rimi.ctibet.domain.Merchant;

/**
 * 用于门户的商户推荐
 * @author xiaozhen
 *
 */

public class MerchantVo {
	 	
	    private String mType;
	    private String mUrl;
	    private List<Merchant> merchantList;
	  
		public String getmType() {
			return mType;
		}
		public void setmType(String mType) {
			this.mType = mType;
		}
		public String getmUrl() {
			return mUrl;
		}
		public void setmUrl(String mUrl) {
			this.mUrl = mUrl;
		}
		public List<Merchant> getMerchantList() {
			return merchantList;
		}
		public void setMerchantList(List<Merchant> merchantList) {
			this.merchantList = merchantList;
		}
		@Override
		public String toString() {
			return "MerchantVo [mType=" + mType + ", mUrl=" + mUrl
					+ ", merchantList=" + merchantList + "]";
		}
}

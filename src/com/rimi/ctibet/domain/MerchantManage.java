package com.rimi.ctibet.domain;

/**门户游西藏首页的商户推荐
 * @author xiaozhen
 */
public class MerchantManage {
	  private Integer id;
	  //推荐位的code
	  private String programaCode;
	  private String merchantTypeCode;
	  //商户的code
	  private String merchantCode1;
	  private String merchantCode2;
	  private String merchantCode3;
	  private String merchantCode4;
	  private Integer available = 1 ;
	  //商户的连接
	  private String merchantUrl1;
	  private String merchantUrl2;
	  private String merchantUrl3;
	  private String merchantUrl4;

	  public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProgramaCode() {
		return programaCode;
	}
	public void setProgramaCode(String programaCode) {
		this.programaCode = programaCode;
	}
	public String getMerchantTypeCode() {
		return merchantTypeCode;
	}
	public void setMerchantTypeCode(String merchantTypeCode) {
		this.merchantTypeCode = merchantTypeCode;
	}
	public String getMerchantCode1() {
		return merchantCode1;
	}
	public void setMerchantCode1(String merchantCode1) {
		this.merchantCode1 = merchantCode1;
	}
	public String getMerchantCode2() {
		return merchantCode2;
	}
	public void setMerchantCode2(String merchantCode2) {
		this.merchantCode2 = merchantCode2;
	}
	public String getMerchantCode3() {
		return merchantCode3;
	}
	public void setMerchantCode3(String merchantCode3) {
		this.merchantCode3 = merchantCode3;
	}
	public String getMerchantCode4() {
		return merchantCode4;
	}
	public void setMerchantCode4(String merchantCode4) {
		this.merchantCode4 = merchantCode4;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public String getMerchantUrl1() {
		return merchantUrl1;
	}
	public void setMerchantUrl1(String merchantUrl1) {
		this.merchantUrl1 = merchantUrl1;
	}
	public String getMerchantUrl2() {
		return merchantUrl2;
	}
	public void setMerchantUrl2(String merchantUrl2) {
		this.merchantUrl2 = merchantUrl2;
	}
	public String getMerchantUrl3() {
		return merchantUrl3;
	}
	public void setMerchantUrl3(String merchantUrl3) {
		this.merchantUrl3 = merchantUrl3;
	}
	public String getMerchantUrl4() {
		return merchantUrl4;
	}
	public void setMerchantUrl4(String merchantUrl4) {
		this.merchantUrl4 = merchantUrl4;
	}
	@Override
	public String toString() {
		return "MerchantManage [available=" + available + ", id=" + id
				+ ", merchantCode1=" + merchantCode1 + ", merchantCode2="
				+ merchantCode2 + ", merchantCode3=" + merchantCode3
				+ ", merchantCode4=" + merchantCode4 + ", merchantTypeCode="
				+ merchantTypeCode + ", merchantUrl1=" + merchantUrl1
				+ ", merchantUrl2=" + merchantUrl2 + ", merchantUrl3="
				+ merchantUrl3 + ", merchantUrl4=" + merchantUrl4
				+ ", programaCode=" + programaCode + "]";
	}
}

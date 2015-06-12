package com.rimi.ctibet.web.controller.vo;

import com.rimi.ctibet.domain.MerchantManage;

public class MerchantManageVo {
	
	private MerchantManage mm1;
	 private MerchantManage mm2;
	 private MerchantManage mm3;
	 private MerchantManage mm4;
	
	 public MerchantManage getMm1() {
		return mm1;
	}
	public void setMm1(MerchantManage mm1) {
		this.mm1 = mm1;
	}
	public MerchantManage getMm2() {
		return mm2;
	}
	public void setMm2(MerchantManage mm2) {
		this.mm2 = mm2;
	}
	public MerchantManage getMm3() {
		return mm3;
	}
	public void setMm3(MerchantManage mm3) {
		this.mm3 = mm3;
	}
	public MerchantManage getMm4() {
		return mm4;
	}
	public void setMm4(MerchantManage mm4) {
		this.mm4 = mm4;
	}
	@Override
	public String toString() {
		return "MerchantManageVo [mm1=" + mm1 + ", mm2=" + mm2 + ", mm3=" + mm3
				+ ", mm4=" + mm4 + "]";
	}
}

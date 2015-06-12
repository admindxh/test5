package com.rimi.ctibet.domain;

import java.util.Date;

import com.rimi.ctibet.common.domain.BaseDomain;
import com.rimi.ctibet.common.util.CodeFactory;

/**
 * 订单
 */
public class CommonOrder extends BaseDomain {
    
	private static final long serialVersionUID = 1L;
	
	//类型{
    /** 装备 */
    public final static String TYPE_EQUIPMENT = "equipment";
    //}
    //来源{
    /** 官方来源 */
    public final static String SOURCE_OFFICAL = "-1";
    //}
    
    //--
    
    public final static int PAY_STATE_NO = 0;
    public final static int PAY_STATE_DONE = 1;
    public final static int PAY_STATE_ALL = 2;
    
    //单号
    private String orderCode;
    //单名
    private String orderName;
    //备注、描述
    private String note;
    //支付状态(0未支付，1支付)
    private int payState = 0;
    //金额
    private float money;
    //支付宝单号
    private String alipayOrderCode;
    //成交时间
    private Date dealTime;
    //付款人支付宝账号
    private String alipayEmail;
    //成功链接
    private String successUrl;
    //失败链接
    private String failureUrl;
    //类型
    private String type;
    //来源
    private String source;
    
    //商品编号
    private String goodsCode;
    //数量
    private int num;
    //收货人
    private String receiver;
    //联系电话
    private String mobile;
    //收货地址
    private String address;
    //邮编
    private String zipcode;
    //会员code
    private String memberCode;
    
    // ===> 帮助属性 S
    /** 会员名称. */
    private String memberName;
    /** 装备详情URL. */
    private String equipUrl;
    // ===> 帮助属性 E

    public CommonOrder() { }
    
    public final static String createCode(){
        return CodeFactory.createCode("COD");
    }
    public final static String createEquipmentCode(){
        return CodeFactory.createCode("EQUIP");
    }

	@Override
    public String toString() {
        return "CommonOrder [orderCode=" + orderCode + ", orderName="
                + orderName + ", note=" + note + ", payState=" + payState
                + ", money=" + money + ", alipayOrderCode=" + alipayOrderCode
                + ", dealTime=" + dealTime + ", alipayEmail=" + alipayEmail
                + ", successUrl=" + successUrl + ", failureUrl=" + failureUrl
                + ", type=" + type + ", source=" + source + ", goodsCode="
                + goodsCode + ", num=" + num + ", receiver=" + receiver
                + ", mobile=" + mobile + ", address=" + address + ", zipcode="
                + zipcode + ", memberCode=" + memberCode + ", toString()="
                + super.toString() + "]";
    }

    /********************************************
     * Setter Getter
     ********************************************/
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getAlipayOrderCode() {
        return alipayOrderCode;
    }

    public void setAlipayOrderCode(String alipayOrderCode) {
        this.alipayOrderCode = alipayOrderCode;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getAlipayEmail() {
        return alipayEmail;
    }

    public void setAlipayEmail(String alipayEmail) {
        this.alipayEmail = alipayEmail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getEquipUrl() {
		return equipUrl;
	}

	public void setEquipUrl(String equipUrl) {
		this.equipUrl = equipUrl;
	}
}

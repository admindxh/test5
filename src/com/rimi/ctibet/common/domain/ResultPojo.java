package com.rimi.ctibet.common.domain;

import com.rimi.ctibet.common.util.ErrorMsgUtil;

/**
 * 接口返回对象实体类
 * 
 * @author sunzy
 * @date Feb 26, 2014
 * @package com.rimi.medical.common.domain
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class ResultPojo {
	private int status = 200;
	private Object data = "";
	private String errorMsg = ErrorMsgUtil.getErrorMsg(200);

	public ResultPojo() {
	}

	public ResultPojo(int status) {
		this.status = status;
		this.errorMsg = ErrorMsgUtil.getErrorMsg(status);
	}

	public ResultPojo(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		this.errorMsg = ErrorMsgUtil.getErrorMsg(status);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}

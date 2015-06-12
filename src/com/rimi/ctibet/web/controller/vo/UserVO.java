package com.rimi.ctibet.web.controller.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import com.rimi.ctibet.domain.UserInfo;

public class UserVO extends UserInfo {

	private RoleVO role;

	public RoleVO getRole() {
		return role;
	}

	public void setRole(RoleVO role) {
		this.role = role;
	}


}

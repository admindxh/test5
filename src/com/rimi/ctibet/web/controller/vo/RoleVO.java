package com.rimi.ctibet.web.controller.vo;

import java.util.List;

import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.domain.Role;

public class RoleVO extends Role {
	private List<Access> accesses;

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}
	
}

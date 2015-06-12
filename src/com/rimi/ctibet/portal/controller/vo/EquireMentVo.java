package com.rimi.ctibet.portal.controller.vo;

import java.util.List;

import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Programa;

public class EquireMentVo {
	
	private Programa  programa ;
	private List<Equipment>  eqlist;
	public Programa getPrograma() {
		return programa;
	}
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	public List<Equipment> getEqlist() {
		return eqlist;
	}
	public void setEqlist(List<Equipment> eqlist) {
		this.eqlist = eqlist;
	}
	
	
	
	
}

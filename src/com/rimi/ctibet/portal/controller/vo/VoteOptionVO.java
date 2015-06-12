package com.rimi.ctibet.portal.controller.vo;

import com.rimi.ctibet.domain.VoteOption;

public class VoteOptionVO extends VoteOption {
	
	int allFakeVoteNum;
	int allVoteNum;
	int percent;
	/********************************************
	 * Setter Getter
	 ********************************************/
	public int getAllFakeVoteNum() {
		return allFakeVoteNum;
	}
	public void setAllFakeVoteNum(int allFakeVoteNum) {
		this.allFakeVoteNum = allFakeVoteNum;
	}
	public int getAllVoteNum() {
		return allVoteNum;
	}
	public void setAllVoteNum(int allVoteNum) {
		this.allVoteNum = allVoteNum;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
}

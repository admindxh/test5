package com.rimi.ctibet.web.service;

import com.rimi.ctibet.domain.PraiseAndViewManager;

public interface IPraiseAndViewManagerService {
	
	//保存记录
	public void saveRecord(String ip,String contentCode);
	//匹配记录(返回false标示没有记录，true标示已有记录)
	public boolean isRecored(String ip,String contentCode);
	//删除记录
	public void deleteRercord(PraiseAndViewManager pvm);
	//根据code查询记录
	public PraiseAndViewManager getRecordByCode(String code);

}

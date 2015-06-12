package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.PraiseAndViewManager;

/**在前端浏览者点赞及浏览时相关管理
 * @author xiaozhen
 */
public interface IPraiseAndViewManagerDao extends BaseDao<PraiseAndViewManager>{

	//保存记录
	public void saveRecord(PraiseAndViewManager pvm);
	//匹配记录(返回false标示没有记录，true标示已有记录)
	public boolean isRecored(PraiseAndViewManager pvm);
	//删除记录
	public void deleteRercord(PraiseAndViewManager pvm);
	//根据code查询记录
	public PraiseAndViewManager getRecordByCode(String code);
}

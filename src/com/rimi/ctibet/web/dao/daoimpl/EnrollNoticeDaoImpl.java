package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.web.dao.IEnrollNoticeDao;

@Repository("enrollNoticeDao")
public class EnrollNoticeDaoImpl extends BaseDaoImpl<EnrollNotice> implements IEnrollNoticeDao {
	
	/**
	 * 通过活动code获取报名须知
	 * @param activityCode
	 * @return
	 */
	public EnrollNotice getEnrollNoticesByActivityCode(String activityCode){
		String sql = " SELECT * FROM enroll_notice WHERE activityCode=? ";
		List<Object> pramas=new ArrayList<Object>();
		pramas.add(activityCode);
		List<EnrollNotice> listEnrollNotice = findListBySql(EnrollNotice.class, sql,pramas);
		return (listEnrollNotice!=null && listEnrollNotice.size()>0)?(listEnrollNotice.get(0)):(null);
	}
}

package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.web.dao.IEnrollFormDao;

@Repository("enrollFormDao")
public class EnrollFormDaoImpl extends BaseDaoImpl<EnrollForm> implements IEnrollFormDao {

	/**
	 * 通过活动code获取报名表单
	 * @param code
	 * @return
	 */
	public List<EnrollForm> getEnrollFormByActivityCode(String activityCode){
	    List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * FROM enroll_form WHERE avaliable=1 AND activityCode=? ORDER BY sortNum ASC ";
		params.add(activityCode);
		return findListBySql(EnrollForm.class, sql, params);
	}
	
}

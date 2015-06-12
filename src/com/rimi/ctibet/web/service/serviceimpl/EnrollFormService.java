package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.web.dao.IEnrollFormDao;
import com.rimi.ctibet.web.service.IEnrollFormService;
@Transactional
@Service("enrollFormService")
public class EnrollFormService extends BaseServiceImpl<EnrollForm> implements IEnrollFormService {

	@Resource IEnrollFormDao enrollFormDao;

	/**
	 * 通过活动code获取报名表单
	 * @param code
	 * @return
	 */
	public List<EnrollForm> getEnrollFormByActivityCode(String activityCode){
		return enrollFormDao.getEnrollFormByActivityCode(activityCode);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IEnrollFormDao getEnrollFormDao() {
		return enrollFormDao;
	}

	public void setEnrollFormDao(IEnrollFormDao enrollFormDao) {
		this.enrollFormDao = enrollFormDao;
	}
	
}

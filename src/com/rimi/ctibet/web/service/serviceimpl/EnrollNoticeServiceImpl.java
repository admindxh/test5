package com.rimi.ctibet.web.service.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.web.dao.IEnrollNoticeDao;
import com.rimi.ctibet.web.service.IEnrollNoticeService;
@Transactional
@Service("enrollNoticeService")
public class EnrollNoticeServiceImpl extends BaseServiceImpl<EnrollNotice> implements IEnrollNoticeService {

	@Resource IEnrollNoticeDao EnrollNoticeDao;

	/**
	 * 通过活动code获取报名须知
	 * @param activityCode
	 * @return
	 */
	public EnrollNotice getEnrollNoticesByActivityCode(String activityCode){
		return EnrollNoticeDao.getEnrollNoticesByActivityCode(activityCode);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IEnrollNoticeDao getEnrollNoticeDao() {
		return EnrollNoticeDao;
	}

	public void setEnrollNoticeDao(IEnrollNoticeDao enrollNoticeDao) {
		EnrollNoticeDao = enrollNoticeDao;
	}
	
}

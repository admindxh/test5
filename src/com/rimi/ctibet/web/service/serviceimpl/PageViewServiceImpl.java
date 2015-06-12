package com.rimi.ctibet.web.service.serviceimpl;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.PageView;
import com.rimi.ctibet.web.dao.IPageViewDao;
import com.rimi.ctibet.web.service.IPageViewService;

/**
 * 访问量统计
 * 
 * @author dengxh
 * 
 */
@Service("pageViewServiceImpl")
@Transactional
public class PageViewServiceImpl extends BaseServiceImpl<PageView> implements
		IPageViewService {
	@Resource
	private IPageViewDao pageViewDao;
	
	/**
	 * 返回数量通过月份
	 */
	@Override
	public Integer getMemCountByTime(final Date start, final Date end) {
		return  pageViewDao.getMemCountByTime(start,end);
	}
	
	public IPageViewDao getPageViewDao() {
		return pageViewDao;
	}

	public void setPageViewDao(IPageViewDao pageViewDao) {
		this.pageViewDao = pageViewDao;
	}

}

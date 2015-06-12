package com.rimi.ctibet.web.service;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.PageView;

/**
 * 访问量统计
 * @author dengxh
 *
 */
public interface IPageViewService  extends BaseService<PageView>{
	/**
	 * 返回数量通过月份
	 */
	public Integer getMemCountByTime(final Date start, final Date end) ;
}
